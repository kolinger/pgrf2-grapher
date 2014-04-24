package me.kolinger.pgrf2.grapher.graphics;

import me.kolinger.pgrf2.grapher.graphics.helpers.HSLColor;
import me.kolinger.pgrf2.grapher.graphics.model.Color;
import me.kolinger.pgrf2.grapher.graphics.model.Point;
import me.kolinger.pgrf2.grapher.graphics.model.Quad;
import me.kolinger.pgrf2.grapher.math.Calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * Basic plot - one function for Z with range for X and Y
 *
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class NormalPlot extends BasePlot {

    private double xFrom = -1;
    private double xTo = 1;
    private double xStep = 0.05;

    private double yFrom = -1;
    private double yTo = 1;
    private double yStep = 0.05;

    private Calculator calculator;

    /**
     * ***************************** accessors *****************************
     */

    public double getXFrom() {
        return xFrom;
    }

    public void setXFrom(double xFrom) {
        this.xFrom = xFrom;
    }

    public double getXTo() {
        return xTo;
    }

    public void setXTo(double xTo) {
        this.xTo = xTo;
    }

    public double getXStep() {
        return xStep;
    }

    public void setXStep(double xStep) {
        this.xStep = xStep;
    }

    public double getYFrom() {
        return yFrom;
    }

    public void setYFrom(double yFrom) {
        this.yFrom = yFrom;
    }

    public double getYTo() {
        return yTo;
    }

    public void setYTo(double yTo) {
        this.yTo = yTo;
    }

    public double getYStep() {
        return yStep;
    }

    public void setYStep(double yStep) {
        this.yStep = yStep;
    }

    public Calculator getCalculator() {
        return calculator;
    }

    public void setCalculator(Calculator calculator) {
        this.calculator = calculator;
    }

    /**
     * ***************************** logic *****************************
     */

    @Override
    protected void processCalculations() {
        if (!isNeedRefresh()) {
            return; // quads are already calculated, skip
        }

        getQuads().clear(); // delete old quads

        // generate new quads
        double minZ = Double.POSITIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;
        Map<Key, Double> buffer = new HashMap<Key, Double>();

        // calculate all Z coordinates, store in buffer for future usage
        for (double y = yFrom; y <= yTo; y += yStep) {
            for (double x = xFrom; x <= xTo; x += xStep) {
                try {
                    Double z = calculator.calculate(x, y);
                    buffer.put(new Key(x, y), z);
                    if (z != null && z != Double.NaN) {
                        if (z < minZ) {
                            minZ = z;
                        }
                        if (z > maxZ) {
                            maxZ = z;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        double z;
        double zScale = maxZ - minZ == 0 ? -360 : -360 / (maxZ - minZ);
        Color color;
        Point a, b, c, d;

        // generate quads with relevant color
        for (double y = yFrom + yStep; y <= yTo; y += yStep) {
            double prevY = y - yStep;

            for (double x = xFrom + xStep; x <= xTo; x += xStep) {
                double prevX = x - xStep;

                z = buffer.get(new Key(x, y));
                color = calculateColorByZ(z, minZ, zScale);
                a = new Point(x, y, z, color);

                z = buffer.get(new Key(x, prevY));
                color = calculateColorByZ(z, minZ, zScale);
                b = new Point(x, prevY, z, color);

                z = buffer.get(new Key(prevX, prevY));
                color = calculateColorByZ(z, minZ, zScale);
                c = new Point(prevX, prevY, z, color);

                z = buffer.get(new Key(prevX, y));
                color = calculateColorByZ(z, minZ, zScale);
                d = new Point(prevX, y, z, color);

                getQuads().add(new Quad(a, b, c, d));
            }
        }

        setNeedRefresh(false); // prevents unnecessary calculations
    }

    /**
     * Helper for storing Z coordinates in map
     */
    public class Key {
        private BigDecimal x;
        private BigDecimal y;

        public Key(double x, double y) {
            this.x = new BigDecimal(x).setScale(2, RoundingMode.HALF_UP);
            this.y = new BigDecimal(y).setScale(2, RoundingMode.HALF_UP);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Key key = (Key) o;

            if (key.x.compareTo(x) != 0) return false;
            if (key.y.compareTo(y) != 0) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = x != null ? x.hashCode() : 0;
            result = 31 * result + (y != null ? y.hashCode() : 0);
            return result;
        }
    }
}

package me.kolinger.pgrf2.grapher.grapher;

import com.jogamp.opengl.util.awt.TextRenderer;
import me.kolinger.pgrf2.grapher.grapher.model.Color;
import me.kolinger.pgrf2.grapher.grapher.model.Point;
import me.kolinger.pgrf2.grapher.grapher.model.Quad;
import me.kolinger.pgrf2.grapher.math.Calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class Plot extends BasePlot {

    private double xFrom = -1;
    private double xTo = 1;
    private double xStep = 0.1;

    private double yFrom = -1;
    private double yTo = 1;
    private double yStep = 0.1;

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
    protected void drawText(TextRenderer renderer) {
        renderer.draw(calculator.getFunction(), 10, 10);
    }

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

        for (double y = yFrom; y <= yTo; y += yStep) {
            for (double x = xFrom; x <= xTo; x += xStep) {
                Double z = calculator.calculate(x, y);
                buffer.put(new Key(x, y), z);
                if (z != null) {
                    if (z < minZ) {
                        minZ = z;
                    }
                    if (z > maxZ) {
                        maxZ = z;
                    }
                }
            }
        }

        double range = Math.abs(minZ) + maxZ;

        double z;
        double colorIntensity;
        Color color;
        Point a, b, c, d;

        for (double y = yFrom + yStep; y <= yTo; y += yStep) {
            double prevY = y - yStep;

            for (double x = xFrom + xStep; x <= xTo; x += xStep) {
                double prevX = x - xStep;

                z = buffer.get(new Key(x, y));
                colorIntensity = 0.008 * ((z / range) * 100);
                if (getColor() == COLOR_RED) {
                    color = new Color(1, colorIntensity, colorIntensity);
                } else if (getColor() == COLOR_GREEN) {
                    color = new Color(colorIntensity, 1, colorIntensity);
                } else {
                    color = new Color(colorIntensity, colorIntensity, 1);
                }
                a = new Point(x, y, z, color);

                z = buffer.get(new Key(x, prevY));
                colorIntensity = 0.008 * ((z / range) * 100);
                if (getColor() == COLOR_RED) {
                    color = new Color(1, colorIntensity, colorIntensity);
                } else if (getColor() == COLOR_GREEN) {
                    color = new Color(colorIntensity, 1, colorIntensity);
                } else {
                    color = new Color(colorIntensity, colorIntensity, 1);
                }
                b = new Point(x, prevY, z, color);

                z = buffer.get(new Key(prevX, prevY));
                colorIntensity = 0.008 * ((z / range) * 100);
                if (getColor() == COLOR_RED) {
                    color = new Color(1, colorIntensity, colorIntensity);
                } else if (getColor() == COLOR_GREEN) {
                    color = new Color(colorIntensity, 1, colorIntensity);
                } else {
                    color = new Color(colorIntensity, colorIntensity, 1);
                }
                c = new Point(prevX, prevY, z, color);

                z = buffer.get(new Key(prevX, y));
                colorIntensity = 0.008 * ((z / range) * 100);
                if (getColor() == COLOR_RED) {
                    color = new Color(1, colorIntensity, colorIntensity);
                } else if (getColor() == COLOR_GREEN) {
                    color = new Color(colorIntensity, 1, colorIntensity);
                } else {
                    color = new Color(colorIntensity, colorIntensity, 1);
                }
                d = new Point(prevX, y, z, color);

                getQuads().add(new Quad(a, b, c, d));
            }
        }

        setNeedRefresh(false); // prevents unnecessary calculations
    }

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

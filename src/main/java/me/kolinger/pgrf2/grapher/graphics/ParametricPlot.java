package me.kolinger.pgrf2.grapher.graphics;

import me.kolinger.pgrf2.grapher.graphics.model.Color;
import me.kolinger.pgrf2.grapher.graphics.model.Point;
import me.kolinger.pgrf2.grapher.graphics.model.Quad;
import me.kolinger.pgrf2.grapher.math.Calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * Parametric plot - three functions for X, Y, Z with range for u and v
 *
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class ParametricPlot extends BasePlot {

    private double uFrom = 0;
    private double uTo = 6.28;
    private double uStep = 0.1;

    private double vFrom = -3.14;
    private double vTo = 3.14;
    private double vStep = 0.1;

    private Calculator xCalculator;
    private Calculator yCalculator;
    private Calculator zCalculator;

    /**
     * ***************************** accessors *****************************
     */

    public double getUFrom() {
        return uFrom;
    }

    public void setUFrom(double uFrom) {
        this.uFrom = uFrom;
    }

    public double getUTo() {
        return uTo;
    }

    public void setUTo(double uTo) {
        this.uTo = uTo;
    }

    public double getUStep() {
        return uStep;
    }

    public void setUStep(double uStep) {
        this.uStep = uStep;
    }

    public double getVFrom() {
        return vFrom;
    }

    public void setVFrom(double vFrom) {
        this.vFrom = vFrom;
    }

    public double getVTo() {
        return vTo;
    }

    public void setVTo(double vTo) {
        this.vTo = vTo;
    }

    public double getVStep() {
        return vStep;
    }

    public void setVStep(double vStep) {
        this.vStep = vStep;
    }

    public Calculator getXCalculator() {
        return xCalculator;
    }

    public void setXCalculator(Calculator xCalculator) {
        this.xCalculator = xCalculator;
    }

    public Calculator getYCalculator() {
        return yCalculator;
    }

    public void setYCalculator(Calculator yCalculator) {
        this.yCalculator = yCalculator;
    }

    public Calculator getZCalculator() {
        return zCalculator;
    }

    public void setZCalculator(Calculator zCalculator) {
        this.zCalculator = zCalculator;
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
        Map<Key, Value> buffer = new HashMap<Key, Value>();

        // calculate all coordinates, store in buffer for future usage
        for (double v = vFrom; v <= vTo + vStep; v += vStep) {
            for (double u = uFrom; u <= uTo + uStep; u += uStep) {
                try {
                    Double x = xCalculator.calculateParametric(u, v);
                    Double y = yCalculator.calculateParametric(u, v);
                    Double z = zCalculator.calculateParametric(u, v);
                    buffer.put(new Key(u, v), new Value(x, y, z));
                    if (z != null) {
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

        double zScale = maxZ - minZ == 0 ? -360 : -360 / (maxZ - minZ);
        Value value;
        Color color;
        Point a, b, c, d, normal;

        // generate quads with relevant color
        for (double v = vFrom + vStep; v <= vTo + vStep; v += vStep) {
            double prevV = v - vStep;

            for (double u = uFrom + uStep; u <= uTo + uStep; u += uStep) {
                double prevU = u - uStep;

                value = buffer.get(new Key(u, v));
                color = calculateColorByZ(value.getZ(), minZ, zScale);
                a = new Point(value.getX(), value.getY(), value.getZ(), color);

                value = buffer.get(new Key(u, prevV));
                color = calculateColorByZ(value.getZ(), minZ, zScale);
                b = new Point(value.getX(), value.getY(), value.getZ(), color);

                value = buffer.get(new Key(prevU, prevV));
                color = calculateColorByZ(value.getZ(), minZ, zScale);
                c = new Point(value.getX(), value.getY(), value.getZ(), color);

                value = buffer.get(new Key(prevU, v));
                color = calculateColorByZ(value.getZ(), minZ, zScale);
                d = new Point(value.getX(), value.getY(), value.getZ(), color);

                double normalX = ((a.getY() - b.getY()) * (a.getZ() + b.getZ()))
                        + ((b.getY() - c.getY()) * (b.getZ() + c.getZ()))
                        + ((c.getY() - d.getY()) * (c.getZ() + d.getZ()))
                        + ((d.getY() - a.getY()) * (d.getZ() + a.getZ()));
                double normalY = ((a.getZ() - b.getZ()) * (a.getX() + b.getX()))
                        + ((b.getZ() - c.getZ()) * (b.getX() + c.getX()))
                        + ((c.getZ() - d.getZ()) * (c.getX() + d.getX()))
                        + ((d.getZ() - a.getZ()) * (d.getX() + a.getX()));
                double normalZ = ((a.getX() - b.getX()) * (a.getY() + b.getY()))
                        + ((b.getX() - c.getX()) * (b.getY() + c.getY()))
                        + ((c.getX() - d.getX()) * (c.getY() + d.getY()))
                        + ((d.getX() - a.getX()) * (d.getY() + a.getY()));
                normal = new Point(normalX, normalY, normalZ);

                getQuads().add(new Quad(a, b, c, d, normal));
            }
        }

        setNeedRefresh(false); // prevents unnecessary calculations
    }

    /**
     * Helpers for storing calculated x, y and z coordinates in map (mapped by u and v values)
     */
    public class Key {
        private BigDecimal u;
        private BigDecimal v;

        public Key(double u, double v) {
            this.u = new BigDecimal(u).setScale(2, RoundingMode.HALF_UP);
            this.v = new BigDecimal(v).setScale(2, RoundingMode.HALF_UP);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Key key = (Key) o;

            if (key.u.compareTo(u) != 0) return false;
            if (key.v.compareTo(v) != 0) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = u != null ? u.hashCode() : 0;
            result = 31 * result + (v != null ? v.hashCode() : 0);
            return result;
        }
    }

    public class Value {
        private double x;
        private double y;
        private double z;

        public Value(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getZ() {
            return z;
        }

        public void setZ(double z) {
            this.z = z;
        }
    }
}

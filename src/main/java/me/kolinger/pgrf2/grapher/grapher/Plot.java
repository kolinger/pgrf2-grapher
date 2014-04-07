package me.kolinger.pgrf2.grapher.grapher;

import me.kolinger.pgrf2.grapher.math.Calculator;

import javax.media.opengl.GL2;

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
    protected void generatePlot(GL2 gl) {
        double minZ = Double.POSITIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;
//        Map<Double, Map<Double, Double>> buffer = new HashMap<Double, Map<Double, Double>>();

        for (double y = yFrom; y <= yTo; y += yStep) {
            for (double x = xFrom; x <= xTo; x += xStep) {
                Double z = calculator.calculate(x, y);
                if (z == null) {
//                    z = Double.POSITIVE_INFINITY;
                } else {
                    if (z < minZ) {
                        minZ = z;
                    }
                    if (z > maxZ) {
                        maxZ = z;
                    }
                }

//                if (buffer.containsKey(y)) {
//                    Map<Double, Double> map = buffer.get(y);
//                    if (!map.containsKey(x)) {
//                        map.put(x, z);
//                    }
//                } else {
//                    Map<Double, Double> map = new HashMap<Double, Double>();
//                    map.put(x, z);
//                    buffer.put(y, map);
//                }
            }
        }

        double range = (Math.abs(minZ) + Math.abs(maxZ));

        for (double y = yFrom + yStep; y <= yTo; y += yStep) {
            double prevY = y - yStep;

            for (double x = xFrom + xStep; x <= xTo; x += xStep) {
                double prevX = x - xStep;

                Double z1 = calculator.calculate(x, y);
                Double z2 = calculator.calculate(x, prevY);
                Double z3 = calculator.calculate(prevX, prevY);
                Double z4 = calculator.calculate(prevX, y);
//                double z1 = buffer.get(y).get(x);
//                double z2 = buffer.get(prevY).get(y);
//                double z3 = buffer.get(prevY).get(prevX);
//                double z4 = buffer.get(y).get(prevX);

                // calculation failed, position is not defined - skip that quad
                if (z1 == Double.POSITIVE_INFINITY || z2 == Double.POSITIVE_INFINITY ||
                        z3 == Double.POSITIVE_INFINITY || z4 == Double.POSITIVE_INFINITY) {

                    continue;
                }

                gl.glBegin(GL2.GL_QUADS);
                double c1 = 0.008 * ((Math.abs(z1) / range) * 100);
                gl.glColor3d(c1, c1, 1);
                gl.glVertex3d(x, y, z1);

                double c2 = 0.008 * ((Math.abs(z2) / range) * 100);
                gl.glColor3d(c2, c2, 1);
                gl.glVertex3d(x, prevY, z2);

                double c3 = 0.008 * ((Math.abs(z3) / range) * 100);
                gl.glColor3d(c3, c3, 1);
                gl.glVertex3d(prevX, prevY, z3);

                double c4 = 0.008 * ((Math.abs(z4) / range) * 100);
                gl.glColor3d(c4, c4, 1);
                gl.glVertex3d(prevX, y, z4);
                gl.glEnd();

                gl.glBegin(GL2.GL_LINES);
                gl.glColor3d(0.7, 0.7, 1);
                gl.glVertex3d(x, y, z1);
                gl.glVertex3d(x, prevY, z2);

                gl.glVertex3d(x, prevY, z2);
                gl.glVertex3d(prevX, prevY, z3);

                gl.glVertex3d(prevX, prevY, z3);
                gl.glVertex3d(prevX, y, z4);

                gl.glVertex3d(prevX, y, z4);
                gl.glVertex3d(x, y, z1);
                gl.glEnd();
            }
        }
    }
}

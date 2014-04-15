package me.kolinger.pgrf2.grapher.math;

import java.util.Vector;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class BuiltInParametricPlots {

    private static Vector<Plot> plots = new Vector<Plot>();

    static {
        plots.add(new Plot(
                "Ring",
                "(3+cos(v))*sin(u)",
                "(3+cos(v))*cos(u)",
                "sin(v)",
                0, 6.28,
                0, 6.28
        ));
        plots.add(new Plot(
                "Star",
                "cos(u)*cos(v)*(abs(cos(3*v/4))^500 + abs(sin(3*v/4))^500)^(-1/260)*(abs(cos(4*u/4))^200 + abs(sin(4*u/4))^200)^(-1/200)",
                "cos(u)*sin(v)*(abs(cos(3*v/4))^500 + abs(sin(3*v/4))^500)^(-1/260)*(abs(cos(4*u/4))^200 + abs(sin(4*u/4))^200)^(-1/200)",
                "sin(u)*(abs(cos(4*u/4))^200 + abs(sin(4*u/4))^200)^(-1/200)",
                -3.14, 3.14,
                0, 6.28
        ));
        plots.add(new Plot(
                "Heart",
                "cos(u)*(4*sqrt(1-v^2)*sin(abs(u))^abs(u))",
                "sin(u) *(4*sqrt(1-v^2)*sin(abs(u))^abs(u))",
                "v",
                -3.14, 3.14,
                -1, 1
        ));
        plots.add(new Plot(
                "Bowtie",
                "sin(u) / (sqrt(2) + sin(v))",
                "sin(u) / (sqrt(2) + cos(v))",
                "cos(u) / (1 + sqrt(2))",
                -3.14, 3.14,
                -3.14, 3.14
        ));
        plots.add(new Plot(
                "Maeder's Owl",
                "v *cos(u) - 0.5* v^2 * cos(2* u)",
                "-v *sin(u) - 0.5* v^2 * sin(2* u)",
                "4 *v^1.5 * cos(3 *u / 2) / 3",
                -6.28, 6.28,
                0, 1
        ));
        plots.add(new Plot(
                "Bracelet",
                "(2 + 0.2*sin(2*pi*u))*sin(pi*v)",
                "0.2*cos(2*pi*u) *3*cos(2*pi*v)",
                "(2 + 0.2*sin(2*pi*u))*cos(pi*v)",
                0, 1.57,
                0, 2.355
        ));
        plots.add(new Plot(
                "Goblet",
                "cos(u)*cos(2*v)",
                "sin(u)*cos(2*v)",
                "sin(v)",
                0, 6.28,
                0, 6.28
        ));
        plots.add(new Plot(
                "Cross cap",
                "(1+cos(v))*cos(u)",
                "(1+cos(v))*sin(u)",
                "-tanh((2/3)*(u-pi))*sin(v)",
                0, 6.28,
                0, 6.28
        ));
        plots.add(new Plot(
                "Whitney umbrella",
                "u*v",
                "u",
                "v^2",
                -1, 1,
                -1, 1
        ));
        plots.add(new Plot(
                "Twisted torus",
                "(3+sin(v)+cos(u))*cos(2*v)",
                "(3+sin(v)+cos(u))*sin(2*v)",
                "sin(u)+2*cos(v)",
                0, 6.28,
                0, 6.28
        ));
        plots.add(new Plot(
                "Discs",
                "v *cos(u) -0.5*v^2*cos(2*u)",
                "-v*sin(u) -0.5*v^2*sin(2*u)",
                "4* v^1.5 *cos(3* u / 2) / 3",
                0, 12.56,
                0, 0.628
        ));
        plots.add(new Plot(
                "Roman surface",
                "(sin(2 * u) * cos(v) * cos(v))",
                "(sin(u) * sin(2 * v))",
                "(cos(u) * sin(2 * v))",
                -1.57, 1.57,
                -1.57, 1.57
        ));
        plots.add(new Plot(
                "Klein bottle",
                "(3*(1+sin(v)) + 2*(1-cos(v)/2)*cos(u))*cos(v)",
                "(4+2*(1-cos(v)/2)*cos(u))*sin(v)",
                "-2*(1-cos(v)/2) * sin(u)",
                0, 6.28,
                0, 6.28
        ));
        plots.add(new Plot(
                "Enneper's surface",
                "u -u^3/3  + u*v^2",
                "v -v^3/3  + v*u^2",
                "u^2 - v^2",
                -2, 2,
                -2, 2
        ));
        plots.add(new Plot(
                "Henneberg's surface",
                "2*sinh(u)*cos(v) -(2/3)*sinh(3*u)*cos(3*v)",
                "2*sinh(u)*sin(v) +(2/3)*sinh(3*u)*sin(3*v)",
                "2*cosh(2*u)*cos(2*v)",
                -1, 1,
                -1.57, 1.57
        ));
        plots.add(new Plot(
                "Dini's spiral",
                "cos(u)*sin(v)",
                "sin(u)*sin(v)",
                "(cos(v)+log(tan(v/2))) + 0.2*u",
                0, 12.4,
                0.1, 2
        ));
        plots.add(new Plot(
                "Catalan's surface",
                "u-sin(u)*cosh(v)",
                "1-cos(u)*cosh(v)",
                "4*sin(1/2*u)*sinh(v/2)",
                -3.14, 9.42,
                -2, 5
        ));
    }

    public static Vector<Plot> getPlots() {
        return plots;
    }

    public static class Plot {
        private String label;
        private String xFunction;
        private String yFunction;
        private String zFunction;
        private double uFrom;
        private double uTo;
        private double vFrom;
        private double vTo;

        public Plot(String label, String xFunction, String yFunction, String zFunction, double uFrom, double uTo, double vFrom, double vTo) {
            this.label = label;
            this.xFunction = xFunction;
            this.yFunction = yFunction;
            this.zFunction = zFunction;
            this.uFrom = uFrom;
            this.uTo = uTo;
            this.vFrom = vFrom;
            this.vTo = vTo;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getXFunction() {
            return xFunction;
        }

        public void setXFunction(String xFunction) {
            this.xFunction = xFunction;
        }

        public String getYFunction() {
            return yFunction;
        }

        public void setYFunction(String yFunction) {
            this.yFunction = yFunction;
        }

        public String getZFunction() {
            return zFunction;
        }

        public void setZFunction(String zFunction) {
            this.zFunction = zFunction;
        }

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

        @Override
        public String toString() {
            return label;
        }
    }
}

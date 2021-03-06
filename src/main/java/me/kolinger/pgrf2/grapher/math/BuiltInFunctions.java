package me.kolinger.pgrf2.grapher.math;

import java.util.Vector;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class BuiltInFunctions {

    private static Vector<Function> functions = new Vector<Function>();

    static {
        functions.add(new Function("Torus", "(0.4^2-(0.6-(x^2+y^2)^0.5)^2)^0.5"));
        functions.add(new Function("Ripple", "sin(10*(x^2+y^2))/10"));
        functions.add(new Function("Bumps", "sin(5*x)*cos(5*y)/5"));
        functions.add(new Function("Cone", "(x^2+y^2)^0.5"));
        functions.add(new Function("Stairs", "(sign(-0.65-x)+sign(-0.35-x)+sign(-0.05-x)+sign(0.25-x)+sign(0.55-x))/7"));
        functions.add(new Function("Roof", "1-abs(y)"));
        functions.add(new Function("Cross", "0.75/exp((x*5)^2*(y*5)^2)"));
        functions.add(new Function("Pyramid", "1-abs(x+y)-abs(y-x)"));
        functions.add(new Function("Triangle", "(1-sign(-x-0.51+abs(y*2)))/3 * (sign(0.5-x)+1)/3"));
    }

    public static Vector<Function> getFunctions() {
        return functions;
    }

    public static class Function {
        private String label;
        private String function;

        public Function(String label, String function) {
            this.label = label;
            this.function = function;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getFunction() {
            return function;
        }

        public void setFunction(String function) {
            this.function = function;
        }

        @Override
        public String toString() {
            return label;
        }
    }
}

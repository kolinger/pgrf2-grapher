package me.kolinger.pgrf2.grapher.math;

import de.congrace.exp4j.CustomFunction;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.InvalidCustomFunctionException;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class Calculator {

    private ExpressionBuilder builder;

    public Calculator(String function) {
        builder = createBuilder(function);
    }

    public Double calculate(double x, double y) {
        try {
            builder.withVariable("x", x);
            builder.withVariable("y", y);
            return builder.build().calculate();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Double calculateParametric(double u, double v) {
        try {
            builder.withVariable("u", u);
            builder.withVariable("v", v);
            return builder.build().calculate();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ExpressionBuilder createBuilder(String expression) {
        try {
            CustomFunction sign = new CustomFunction("sign") {
                public double applyFunction(double[] values) {
                    return Math.signum(values[0]);
                }
            };

            CustomFunction acsc = new CustomFunction("acsc") {
                public double applyFunction(double[] values) {
                    return Math.asin(-1 / values[0]);
                }
            };

            return new ExpressionBuilder(expression)
                    .withCustomFunction(sign)
                    .withCustomFunction(acsc)
                    .withVariable("pi", Math.PI)
                    .withVariable("e", Math.E);

        } catch (InvalidCustomFunctionException e) {
            return null;
        }
    }
}

package me.kolinger.pgrf2.grapher.math;

import de.congrace.exp4j.CustomFunction;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.InvalidCustomFunctionException;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class Calculator {

    private ExpressionBuilder builder;
    private String function;

    public Calculator(String function) {
        builder = createBuilder(function);
        this.function = function;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public Double calculate(double x, double y) throws Exception{
        builder.withVariable("x", x);
        builder.withVariable("y", y);
        return builder.build().calculate();
    }

    public Double calculateParametric(double u, double v) throws Exception{
        builder.withVariable("u", u);
        builder.withVariable("v", v);
        return builder.build().calculate();
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

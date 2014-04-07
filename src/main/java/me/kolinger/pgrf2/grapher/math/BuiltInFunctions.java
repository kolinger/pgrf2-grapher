package me.kolinger.pgrf2.grapher.math;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class BuiltInFunctions {

    private static String[] functions = new String[]{
            "x^2+y^2",
            "(0.4^2-(0.6-(x^2+y^2)^0.5)^2)^0.5",
            "sin(10*(x^2+y^2))/10",
            "sin(5*x)*cos(5*y)/5",
            "(x^2+y^2)^0.5",
            "(sign(-0.65-x)+sign(-0.35-x)+sign(-0.05-x)+sign(0.25-x)+sign(0.55-x))/7",
            "1-abs(x+y)-abs(y-x)",
            "0.1 - sign(sign((x*12)^2-9)-1 + sign((y*12)^2-9)-1)/2",
            "1-abs(y)",
            "(1-acsc((x*4)^2+(y*4)^2))/1000",
            "(1-sign(-x-0.51+abs(y*2)))/3 * (sign(0.5-x)+1)/3",
            "(sign(0.2-(x^2+y^2)) + sign(0.2-(x^2/3+y^2/3)))/3-1",
            "((1-sign(-x-0.9+abs(y*2)))/3*(sign(0.9-x)+1)/3)*(sign(x+0.65)+1)/2 - ((1-sign(-x-0.39+abs(y*2)))/3*(sign(0.9-x)+1)/3) + ((1-sign(-x-0.39+abs(y*2)))/3*(sign(0.6-x)+1)/3)*(sign(x-0.35)+1)/2",
            "(-sign(0.2-(x^2+y^2)) + sign(0.2-(x^2/3+y^2/3)))/9",
            "sign(x-1+abs(y*2))/3 + sign(x-0.5+abs(y*2))/3",
            "0.75/exp((x*5)^2*(y*5)^2)",
    };

    private static int pointer = -1;

    public static String getNextFunction() {
        pointer++;
        if (pointer >= functions.length) {
            pointer = 0;
        }

        return functions[pointer];
    }
}

package me.kolinger.pgrf2.grapher.graphics;

import me.kolinger.pgrf2.grapher.math.Calculator;

/**
 * Parametric plot - three functions for X, Y, Z with range for u and v
 *
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public abstract class ParametricPlot extends BasePlot {

    private int uFrom = -5;
    private int uTo = 5;
    private int uStep = 1;

    private int vFrom = -5;
    private int vTo = 5;
    private int vStep = 1;

    private Calculator xCalculator;
    private Calculator yCalculator;
    private Calculator zCalculator;

    /**
     * ***************************** accessors *****************************
     */

    public int getUFrom() {
        return uFrom;
    }

    public void setUFrom(int uFrom) {
        this.uFrom = uFrom;
    }

    public int getUTo() {
        return uTo;
    }

    public void setUTo(int uTo) {
        this.uTo = uTo;
    }

    public int getUStep() {
        return uStep;
    }

    public void setUStep(int uStep) {
        this.uStep = uStep;
    }

    public int getVFrom() {
        return vFrom;
    }

    public void setVFrom(int vFrom) {
        this.vFrom = vFrom;
    }

    public int getVTo() {
        return vTo;
    }

    public void setVTo(int vTo) {
        this.vTo = vTo;
    }

    public int getVStep() {
        return vStep;
    }

    public void setVStep(int vStep) {
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


}

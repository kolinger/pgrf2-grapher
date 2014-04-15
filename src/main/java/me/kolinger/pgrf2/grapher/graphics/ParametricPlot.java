package me.kolinger.pgrf2.grapher.graphics;

import me.kolinger.pgrf2.grapher.math.Calculator;

/**
 * Parametric plot - three functions for X, Y, Z with range for u and v
 *
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class ParametricPlot extends BasePlot {

    private double uFrom = 0;
    private double uTo = 3.14;
    private double uStep = 0.01;

    private double vFrom = -3.14;
    private double vTo = 3.14;
    private double vStep = 0.01;

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

    }
}

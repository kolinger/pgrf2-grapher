package me.kolinger.pgrf2.grapher.gui;

import javax.swing.JFrame;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class MainWindow extends JFrame implements Runnable {

    final public static int MODE_Z = 0;
    final public static int MODE_PARAMETRIC = 1;

    private BasePlotWindow plotWindow;
    private int mode = MODE_Z;

    public BasePlotWindow getPlotWindow() {
        return plotWindow;
    }

    public void setPlotWindow(BasePlotWindow plotWindow) {
        this.plotWindow = plotWindow;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    public void run() {
        if (mode == MODE_Z) {
            plotWindow = new NormalPlotWindow(this);
        } else {
            plotWindow = new ParametricPlotWindow(this);
        }
        plotWindow.setVisible(true);
    }

    public void switchToNormalMode() {
        plotWindow.setVisible(false);
        plotWindow.getAnimator().stop();
        plotWindow = new NormalPlotWindow(this);
        plotWindow.setVisible(true);
    }

    public void switchToParametricMode() {
        plotWindow.setVisible(false);
        plotWindow.getAnimator().stop();
        plotWindow = new ParametricPlotWindow(this);
        plotWindow.setVisible(true);
    }
}

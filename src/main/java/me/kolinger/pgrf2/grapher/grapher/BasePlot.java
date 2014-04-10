package me.kolinger.pgrf2.grapher.grapher;


import com.jogamp.opengl.util.awt.TextRenderer;
import me.kolinger.pgrf2.grapher.grapher.model.Quad;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public abstract class BasePlot {

    final public static int COLOR_RED = 0;
    final public static int COLOR_GREEN = 1;
    final public static int COLOR_BLUE = 2;

    private int color = COLOR_BLUE;
    private boolean linesEnabled = true;
    private boolean fillEnabled = true;
    private boolean axesEnabled = true;
    private int rotateX = 0;
    private int rotateY = 0;
    private double distance = 5;
    private boolean needRefresh = true;
    private List<Quad> quads = new ArrayList<Quad>();
    private Listener listener = new Listener(this);

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isLinesEnabled() {
        return linesEnabled;
    }

    public void setLinesEnabled(boolean linesEnabled) {
        this.linesEnabled = linesEnabled;
    }

    public boolean isFillEnabled() {
        return fillEnabled;
    }

    public void setFillEnabled(boolean fillEnabled) {
        this.fillEnabled = fillEnabled;
    }

    public boolean isAxesEnabled() {
        return axesEnabled;
    }

    public void setAxesEnabled(boolean axesEnabled) {
        this.axesEnabled = axesEnabled;
    }

    public int getRotateX() {
        return rotateX;
    }

    public void setRotateX(int rotateX) {
        this.rotateX = rotateX;
    }

    public int getRotateY() {
        return rotateY;
    }

    public void setRotateY(int rotateY) {
        this.rotateY = rotateY;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public boolean isNeedRefresh() {
        return needRefresh;
    }

    public void setNeedRefresh(boolean needRefresh) {
        this.needRefresh = needRefresh;
    }

    public List<Quad> getQuads() {
        return quads;
    }

    public void setQuads(List<Quad> quads) {
        this.quads = quads;
    }

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    protected abstract void processCalculations();

    protected abstract void drawText(TextRenderer renderer);
}

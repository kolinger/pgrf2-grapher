package me.kolinger.pgrf2.grapher.grapher;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public abstract class BasePlot {

    private Listener listener = new Listener(this);

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    protected abstract void generatePlot(GL2 gl);
    protected abstract void drawInfo(GLAutoDrawable glAutoDrawable);
}

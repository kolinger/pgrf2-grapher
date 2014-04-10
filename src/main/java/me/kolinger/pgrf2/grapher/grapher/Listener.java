package me.kolinger.pgrf2.grapher.grapher;


import com.jogamp.opengl.util.awt.TextRenderer;
import me.kolinger.pgrf2.grapher.grapher.model.Color;
import me.kolinger.pgrf2.grapher.grapher.model.Point;
import me.kolinger.pgrf2.grapher.grapher.model.Quad;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.awt.Font;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class Listener implements GLEventListener {

    private GLU glu;
    private BasePlot plot;
    private TextRenderer textRenderer = new TextRenderer(new Font("SansSerif", Font.PLAIN, 16));

    public Listener(BasePlot plot) {
        this.plot = plot;
    }

    @Override
    public void display(GLAutoDrawable glDrawable) {
        GL2 gl = glDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslated(0, 0, -plot.getDistance());

        gl.glRotatef(plot.getRotateX(), 1.0f, 0.0f, 0.0f);
        gl.glRotatef(plot.getRotateY(), 0.0f, 1.0f, 0.0f);

        plot.processCalculations();

        textRenderer.beginRendering(glDrawable.getWidth(), glDrawable.getHeight());
        textRenderer.setColor(1, 1, 1, 1);
        plot.drawText(textRenderer);
        textRenderer.endRendering();

        for (Quad quad : plot.getQuads()) {
            if (plot.isFillEnabled()) {
                gl.glBegin(GL2.GL_QUADS);
                addQuadVertexHelper(gl, quad.getA());
                addQuadVertexHelper(gl, quad.getB());
                addQuadVertexHelper(gl, quad.getC());
                addQuadVertexHelper(gl, quad.getD());
                gl.glEnd();
            }

            if (plot.isLinesEnabled()) {
                gl.glBegin(GL2.GL_LINES);
                if (plot.getColor() == BasePlot.COLOR_RED) {
                    gl.glColor3d(1, 0.7, 0.7);
                } else if (plot.getColor() == BasePlot.COLOR_GREEN) {
                    gl.glColor3d(0.7, 1, 0.7);
                } else {
                    gl.glColor3d(0.7, 0.7, 1);
                }

                addLineVertexHelper(gl, quad.getA(), quad.getB());
                addLineVertexHelper(gl, quad.getB(), quad.getC());
                addLineVertexHelper(gl, quad.getC(), quad.getD());
                addLineVertexHelper(gl, quad.getD(), quad.getA());
                gl.glEnd();
            }
        }

        if (plot.isAxesEnabled()) {
            gl.glBegin(GL2.GL_LINES);
            gl.glColor3f(1, 1, 1);

            gl.glVertex3f(1, 0, 0);
            gl.glVertex3f(-1, 0, 0);

            gl.glVertex3f(0, 1, 0);
            gl.glVertex3f(0, -1, 0);

            gl.glVertex3f(0, 0, 1);
            gl.glVertex3f(0, 0, -1);
            gl.glEnd();
        }
    }

    private void addLineVertexHelper(GL2 gl, Point a, Point b) {
        gl.glVertex3d(a.getX(), a.getY(), a.getZ());
        gl.glVertex3d(b.getX(), b.getY(), b.getZ());
    }

    private void addQuadVertexHelper(GL2 gl, Point point) {
        Color color = point.getColor();
        gl.glColor3d(color.getRed(), color.getGreen(), color.getBlue());
        gl.glVertex3d(point.getX(), point.getY(), point.getZ());
    }

    @Override
    public void init(GLAutoDrawable glDrawable) {
        glu = new GLU();
        GL2 gl = glDrawable.getGL().getGL2();
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL2.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

        gl.glEnable(GL2.GL_LINE_SMOOTH);
        gl.glHint(GL2.GL_LINE_SMOOTH_HINT, GL2.GL_NICEST);

        gl.glEnable(GL2.GL_POLYGON_SMOOTH);
        gl.glHint(GL2.GL_POLYGON_SMOOTH_HINT, GL2.GL_NICEST);
    }

    @Override
    public void reshape(GLAutoDrawable glDrawable, int x, int y, int width, int height) {
        GL2 gl = glDrawable.getGL().getGL2();

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        double aspect = width / height;
        double fh = 0.5;
        double fw = fh * aspect;
        gl.glFrustum(-fw, fw, -fh, fh, 1, 1000);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void dispose(GLAutoDrawable gLDrawable) {

    }
}

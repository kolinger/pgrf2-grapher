package me.kolinger.pgrf2.grapher.graphics;


import me.kolinger.pgrf2.grapher.graphics.model.Color;
import me.kolinger.pgrf2.grapher.graphics.model.Point;
import me.kolinger.pgrf2.grapher.graphics.model.Quad;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class Listener implements GLEventListener {

    private GL2 gl;
    private GLU glu;
    private BasePlot plot;

    private int width;
    private int height;

    public Listener(BasePlot plot) {
        this.plot = plot;
    }

    @Override
    public void init(GLAutoDrawable glDrawable) {
        glu = new GLU();
        gl = glDrawable.getGL().getGL2();

        // initialization - set some base values
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glClearColor(0, 0, 0, 0);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

        // enable anti-aliasing
        gl.glEnable(GL2.GL_LINE_SMOOTH);
        gl.glHint(GL2.GL_LINE_SMOOTH_HINT, GL2.GL_NICEST);

        gl.glEnable(GL2.GL_POLYGON_SMOOTH);
        gl.glHint(GL2.GL_POLYGON_SMOOTH_HINT, GL2.GL_NICEST);

        // enable automatic normalization of normals
        gl.glEnable(GL2.GL_NORMALIZE);
        gl.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE, GL2.GL_TRUE);

        width = glDrawable.getWidth();
        height = glDrawable.getHeight();
    }

    @Override
    public void reshape(GLAutoDrawable glDrawable, int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void dispose(GLAutoDrawable gLDrawable) {
    }

    @Override
    public void display(GLAutoDrawable glDrawable) {
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        // set projection matrix
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        double aspect = width / (double) height;
        gl.glOrtho(-plot.getDistance() * aspect, plot.getDistance() * aspect,
                -plot.getDistance(), plot.getDistance(), 0.1, 1000);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        // enable light if needed
        if (plot.isLightEnabled()) {
            gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, new float[]{0, 0, 0, -(float) plot.getDistance()}, 0);

            gl.glEnable(GL2.GL_LIGHTING);
            gl.glEnable(GL2.GL_LIGHT0);
        }

        glu.gluLookAt(0, 0, 50, 0, 0, 0, 0, 1, 0);

        // rotation
        gl.glRotated(plot.getRotateX(), 1, 0, 0);
        gl.glRotated(plot.getRotateY(), 0, 1, 0);

        // draw function from quads
        plot.processCalculations();

        for (Quad quad : plot.getQuads()) {
            if (plot.isFillEnabled()) {
                gl.glBegin(GL2.GL_QUADS);
                // calculate normals and material if is light enabled
                if (plot.isLightEnabled()) {
                    double normalX = ((quad.getA().getY() - quad.getB().getY()) * (quad.getA().getZ() + quad.getB().getZ()))
                            + ((quad.getB().getY() - quad.getC().getY()) * (quad.getB().getZ() + quad.getC().getZ()))
                            + ((quad.getC().getY() - quad.getD().getY()) * (quad.getC().getZ() + quad.getD().getZ()))
                            + ((quad.getD().getY() - quad.getA().getY()) * (quad.getD().getZ() + quad.getA().getZ()));
                    double normalY = ((quad.getA().getZ() - quad.getB().getZ()) * (quad.getA().getX() + quad.getB().getX()))
                            + ((quad.getB().getZ() - quad.getC().getZ()) * (quad.getB().getX() + quad.getC().getX()))
                            + ((quad.getC().getZ() - quad.getD().getZ()) * (quad.getC().getX() + quad.getD().getX()))
                            + ((quad.getD().getZ() - quad.getA().getZ()) * (quad.getD().getX() + quad.getA().getX()));
                    double normalZ = ((quad.getA().getX() - quad.getB().getX()) * (quad.getA().getY() + quad.getB().getY()))
                            + ((quad.getB().getX() - quad.getC().getX()) * (quad.getB().getY() + quad.getC().getY()))
                            + ((quad.getC().getX() - quad.getD().getX()) * (quad.getC().getY() + quad.getD().getY()))
                            + ((quad.getD().getX() - quad.getA().getX()) * (quad.getD().getY() + quad.getA().getY()));

                    gl.glNormal3d(-normalX, -normalY, -normalZ);
                    gl.glNormal3d(normalX, normalY, normalZ);

                    float[] color;
                    if (plot.getColor() == BasePlot.COLOR_RED) {
                        color = new float[]{1, 0.1f, 0.1f};
                    } else if (plot.getColor() == BasePlot.COLOR_GREEN) {
                        color = new float[]{0.1f, 1, 0.1f};
                    } else {
                        color = new float[]{0.1f, 0.1f, 1};
                    }
                    gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, color, 0);
                }

                addQuadVertexHelper(quad.getA());
                addQuadVertexHelper(quad.getB());
                addQuadVertexHelper(quad.getC());
                addQuadVertexHelper(quad.getD());
                gl.glEnd();
            }

            if (plot.isLinesEnabled()) {
                gl.glBegin(GL2.GL_LINES);
                if (!plot.isLightEnabled()) {
                    gl.glColor3d(0, 0, 0);
                }

                addLineVertexHelper(quad.getA(), quad.getB());
                addLineVertexHelper(quad.getB(), quad.getC());
                addLineVertexHelper(quad.getC(), quad.getD());
                addLineVertexHelper(quad.getD(), quad.getA());
                gl.glEnd();
            }
        }

        // axes and info text
        gl.glDisable(GL2.GL_LIGHTING);

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

    /**
     * ***************************** Helpers *****************************
     */

    private void addLineVertexHelper(Point a, Point b) {
        gl.glVertex3d(a.getX(), a.getY(), a.getZ());
        gl.glVertex3d(b.getX(), b.getY(), b.getZ());
    }

    private void addQuadVertexHelper(Point point) {
        if (!plot.isLightEnabled()) {
            Color color = point.getColor();
            gl.glColor3d(color.getRed(), color.getGreen(), color.getBlue());
        }
        gl.glVertex3d(point.getX(), point.getY(), point.getZ());
    }
}

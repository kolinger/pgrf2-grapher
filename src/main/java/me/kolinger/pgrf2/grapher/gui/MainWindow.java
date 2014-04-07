package me.kolinger.pgrf2.grapher.gui;

import com.jogamp.opengl.util.FPSAnimator;
import me.kolinger.pgrf2.grapher.grapher.Plot;
import me.kolinger.pgrf2.grapher.math.BuiltInFunctions;
import me.kolinger.pgrf2.grapher.math.Calculator;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class MainWindow extends JFrame implements Runnable {

    private Integer startX;
    private Integer startY;
    private Integer savedDiffX = 0;
    private Integer savedDiffY = 0;

    @Override
    public void run() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setup();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void setup() {
        GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities(glprofile);
        GLCanvas canvas = new GLCanvas(glcapabilities);
        final Plot plot = new Plot();
        plot.setCalculator(new Calculator(BuiltInFunctions.getNextFunction()));
        canvas.addGLEventListener(plot.getListener());

        canvas.requestFocus();

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                startX = e.getX();
                startY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mousePressed(e);
                savedDiffX = plot.getListener().getRotateY();
                savedDiffY = plot.getListener().getRotateX();
            }

        });

        canvas.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e){
                if(e.getWheelRotation() > 0) {
                    plot.getListener().setDistance(plot.getListener().getDistance() + 0.1);
                } else {
                    plot.getListener().setDistance(plot.getListener().getDistance() - 0.1);
                }
            }
        });

        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseMoved(e);

                Integer diffX = (int) ((startX - e.getX()) * 0.1);
                Integer diffY = (int) ((startY - e.getY()) * 0.1);

                plot.getListener().setRotateX(savedDiffY - diffY);
                plot.getListener().setRotateY(savedDiffX - diffX);
            }
        });

        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    plot.setCalculator(new Calculator(BuiltInFunctions.getNextFunction()));
                }
            }
        });

        add(canvas);

        FPSAnimator animator = new FPSAnimator(25);
        animator.add(canvas);
        animator.start();
    }
}

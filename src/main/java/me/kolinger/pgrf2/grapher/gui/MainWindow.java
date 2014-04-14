package me.kolinger.pgrf2.grapher.gui;

import com.jogamp.opengl.util.FPSAnimator;
import me.kolinger.pgrf2.grapher.grapher.Plot;
import me.kolinger.pgrf2.grapher.math.BuiltInFunctions;
import me.kolinger.pgrf2.grapher.math.Calculator;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.awt.GLJPanel;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

    private Integer startX = 0;
    private Integer startY = 0;
    private Integer savedDiffX = 0;
    private Integer savedDiffY = 0;

    @Override
    public void run() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setup();
        setTitle("3D Grapher");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private void setup() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(buildTopControls(), gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(buildCanvas(), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(buildSideControls(), gbc);
        add(panel);
    }

    private GLJPanel buildCanvas() {
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
                savedDiffX = plot.getRotateY();
                savedDiffY = plot.getRotateX();
            }

        });

        canvas.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() > 0) {
                    plot.setDistance(plot.getDistance() + 0.1);
                } else {
                    plot.setDistance(plot.getDistance() - 0.1);
                }
            }
        });

        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseMoved(e);

                Integer diffX = (int) ((startX - e.getX()) * 0.1);
                Integer diffY = (int) ((startY - e.getY()) * 0.1);

                plot.setRotateX(savedDiffY - diffY);
                plot.setRotateY(savedDiffX - diffX);
            }
        });

        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_N) {
                    plot.setCalculator(new Calculator(BuiltInFunctions.getNextFunction()));
                    plot.setNeedRefresh(true);
                } else if (e.getKeyCode() == KeyEvent.VK_A) {
                    plot.setAxesEnabled(!plot.isAxesEnabled());
                } else if (e.getKeyCode() == KeyEvent.VK_L) {
                    plot.setLinesEnabled(!plot.isLinesEnabled());
                } else if (e.getKeyCode() == KeyEvent.VK_F) {
                    plot.setFillEnabled(!plot.isFillEnabled());
                }
            }
        });

        FPSAnimator animator = new FPSAnimator(25);
        animator.add(canvas);
        animator.start();

        canvas.setSize(800, 600);
        GLJPanel panel = new GLJPanel();
        panel.add(canvas);
        return panel;
    }

    private JPanel buildSideControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // lines
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Lines: "), gbc);

        gbc.gridx = 1;
        JCheckBox linesCheckbox = new JCheckBox();
        panel.add(linesCheckbox, gbc);

        // fill
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Fill: "), gbc);

        gbc.gridx = 1;
        JCheckBox fillCheckbox = new JCheckBox();
        panel.add(fillCheckbox, gbc);

        // light
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Light: "), gbc);

        gbc.gridx = 1;
        JCheckBox lightCheckbox = new JCheckBox();
        panel.add(lightCheckbox, gbc);

        return panel;
    }
    private JPanel buildTopControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Function: "), gbc);

        gbc.gridx = 1;
        JTextField functionField = new JTextField(null, 60);
        panel.add(functionField, gbc);

        return panel;
    }
}

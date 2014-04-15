package me.kolinger.pgrf2.grapher.gui;

import com.jogamp.opengl.util.AnimatorBase;
import com.jogamp.opengl.util.FPSAnimator;
import me.kolinger.pgrf2.grapher.graphics.BasePlot;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public abstract class BasePlotWindow extends JFrame implements Runnable {

    private MainWindow mainWindow;
    private BasePlot plot;
    private AnimatorBase animator;

    protected Integer startX = 0;
    protected Integer startY = 0;
    protected Integer savedDiffX = 0;
    protected Integer savedDiffY = 0;

    protected JCheckBox linesCheckbox;
    protected JCheckBox lightCheckbox;
    protected JCheckBox fillCheckbox;
    protected JComboBox colorBox;

    public BasePlotWindow() {
    }

    public BasePlotWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    /**
     * ***************************** accessors *****************************
     */

    public MainWindow getMainWindow() {
        return mainWindow;
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public BasePlot getPlot() {
        return plot;
    }

    public void setPlot(BasePlot plot) {
        this.plot = plot;
    }

    public AnimatorBase getAnimator() {
        return animator;
    }

    public void setAnimator(AnimatorBase animator) {
        this.animator = animator;
    }

    public Integer getStartX() {
        return startX;
    }

    public void setStartX(Integer startX) {
        this.startX = startX;
    }

    public Integer getStartY() {
        return startY;
    }

    public void setStartY(Integer startY) {
        this.startY = startY;
    }

    public Integer getSavedDiffX() {
        return savedDiffX;
    }

    public void setSavedDiffX(Integer savedDiffX) {
        this.savedDiffX = savedDiffX;
    }

    public Integer getSavedDiffY() {
        return savedDiffY;
    }

    public void setSavedDiffY(Integer savedDiffY) {
        this.savedDiffY = savedDiffY;
    }

    public JCheckBox getLinesCheckbox() {
        return linesCheckbox;
    }

    public void setLinesCheckbox(JCheckBox linesCheckbox) {
        this.linesCheckbox = linesCheckbox;
    }

    public JCheckBox getLightCheckbox() {
        return lightCheckbox;
    }

    public void setLightCheckbox(JCheckBox lightCheckbox) {
        this.lightCheckbox = lightCheckbox;
    }

    public JCheckBox getFillCheckbox() {
        return fillCheckbox;
    }

    public void setFillCheckbox(JCheckBox fillCheckbox) {
        this.fillCheckbox = fillCheckbox;
    }

    public JComboBox getColorBox() {
        return colorBox;
    }

    public void setColorBox(JComboBox colorBox) {
        this.colorBox = colorBox;
    }

    /**
     * ***************************** logic *****************************
     */

    @Override
    public void run() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setup();
        setTitle("3D Grapher");
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    protected void setup() {
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

    protected Component buildCanvas() {
        GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities(glprofile);
        GLCanvas canvas = new GLCanvas(glcapabilities);
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

        animator = new FPSAnimator(25);
        animator.add(canvas);
        animator.start();

        canvas.setSize(800, 600);
        return canvas;
    }

    protected abstract Component buildTopControls();

    protected abstract Component buildSideControls();
}

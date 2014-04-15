package me.kolinger.pgrf2.grapher.gui;

import com.jogamp.opengl.util.FPSAnimator;
import me.kolinger.pgrf2.grapher.graphics.Plot;
import me.kolinger.pgrf2.grapher.math.BuiltInFunctions;
import me.kolinger.pgrf2.grapher.math.Calculator;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.awt.GLJPanel;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class MainWindow extends JFrame implements Runnable {

    private Plot plot;

    // canvas
    private Integer startX = 0;
    private Integer startY = 0;
    private Integer savedDiffX = 0;
    private Integer savedDiffY = 0;

    // UI
    private JTextField functionField;
    private JCheckBox linesCheckbox;
    private JCheckBox lightCheckbox;
    private JCheckBox fillCheckbox;
    private JComboBox builtInFunctions;
    private JComboBox colorBox;

    // basic plot
    private JTextField xFromField;
    private JTextField xToField;
    private JTextField xStepField;

    private JTextField yFromField;
    private JTextField yToField;
    private JTextField yStepField;

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

    private GLCanvas buildCanvas() {
        GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities(glprofile);
        GLCanvas canvas = new GLCanvas(glcapabilities);
        plot = new Plot();
        BuiltInFunctions.Function function = (BuiltInFunctions.Function) builtInFunctions.getSelectedItem();
        functionField.setText(function.getFunction());
        plot.setCalculator(new Calculator(function.getFunction()));
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

        FPSAnimator animator = new FPSAnimator(25);
        animator.add(canvas);
        animator.start();

        canvas.setSize(800, 600);
        return canvas;
    }

    private JPanel buildSideControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // color
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Color: "), gbc);

        gbc.gridx = 1;
        colorBox = new JComboBox(new String[]{"Red", "Green", "Blue"});
        colorBox.setSelectedIndex(plot.getColor());
        panel.add(colorBox, gbc);

        // lines
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Lines: "), gbc);

        gbc.gridx = 1;
        linesCheckbox = new JCheckBox();
        linesCheckbox.setSelected(plot.isLinesEnabled());
        panel.add(linesCheckbox, gbc);

        // fill
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Fill: "), gbc);

        gbc.gridx = 1;
        fillCheckbox = new JCheckBox();
        fillCheckbox.setSelected(plot.isFillEnabled());
        panel.add(fillCheckbox, gbc);

        // light
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Light: "), gbc);

        gbc.gridx = 1;
        lightCheckbox = new JCheckBox();
        lightCheckbox.setSelected(plot.isLightEnabled());
        panel.add(lightCheckbox, gbc);

        // x from
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("X from: "), gbc);

        gbc.gridx = 1;
        xFromField = new JTextField();
        xFromField.setText(String.valueOf(plot.getXFrom()));
        panel.add(xFromField, gbc);

        // x to
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("X to: "), gbc);

        gbc.gridx = 1;
        xToField = new JTextField();
        xToField.setText(String.valueOf(plot.getXTo()));
        panel.add(xToField, gbc);

        // x step
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("X step: "), gbc);

        gbc.gridx = 1;
        xStepField = new JTextField();
        xStepField.setText(String.valueOf(plot.getXStep()));
        panel.add(xStepField, gbc);

        // y from
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("Y from: "), gbc);

        gbc.gridx = 1;
        yFromField = new JTextField();
        yFromField.setText(String.valueOf(plot.getYFrom()));
        panel.add(yFromField, gbc);

        // y to
        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(new JLabel("Y to: "), gbc);

        gbc.gridx = 1;
        yToField = new JTextField();
        yToField.setText(String.valueOf(plot.getYTo()));
        panel.add(yToField, gbc);

        // y step
        gbc.gridx = 0;
        gbc.gridy = 9;
        panel.add(new JLabel("Y step: "), gbc);

        gbc.gridx = 1;
        yStepField = new JTextField();
        yStepField.setText(String.valueOf(plot.getYStep()));
        panel.add(yStepField, gbc);

        // button
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // checkboxes
                plot.setFillEnabled(fillCheckbox.isSelected());
                plot.setLinesEnabled(linesCheckbox.isSelected());
                plot.setLightEnabled(lightCheckbox.isSelected());

                // color
                int color = colorBox.getSelectedIndex();
                if (color != plot.getColor()) {
                    plot.setColor(color);
                }

                // function
                if (functionField.getText() != null) {
                    String function = functionField.getText().trim();
                    if (function.length() > 0 && !function.equals(plot.getCalculator().getFunction().trim())) {
                        Calculator calculator = new Calculator(function);
                        try {
                            calculator.calculate(0, 0);
                            plot.setCalculator(calculator);
                            plot.setNeedRefresh(true);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Error - function is invalid",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }

                // ranges
                if (xFromField.getText() != null) {
                    try {
                        plot.setXFrom(Double.valueOf(xFromField.getText().trim()));
                        plot.setNeedRefresh(true);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error - X from must be valid number",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                if (xToField.getText() != null) {
                    try {
                        plot.setXTo(Double.valueOf(xToField.getText().trim()));
                        plot.setNeedRefresh(true);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error - X to must be valid number",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                if (xStepField.getText() != null) {
                    try {
                        plot.setXStep(Double.valueOf(xStepField.getText().trim()));
                        plot.setNeedRefresh(true);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error - X step must be valid number",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                if (yFromField.getText() != null) {
                    try {
                        plot.setYFrom(Double.valueOf(yFromField.getText().trim()));
                        plot.setNeedRefresh(true);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error - Y from must be valid number",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                if (yToField.getText() != null) {
                    try {
                        plot.setYTo(Double.valueOf(yToField.getText().trim()));
                        plot.setNeedRefresh(true);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error - Y to must be valid number",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                if (yStepField.getText() != null) {
                    try {
                        plot.setYStep(Double.valueOf(yStepField.getText().trim()));
                        plot.setNeedRefresh(true);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error - Y step must be valid number",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        panel.add(applyButton, gbc);

        return panel;
    }

    private JPanel buildTopControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // function
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Function: "), gbc);

        gbc.gridx = 1;
        functionField = new JTextField(null, 60);
        panel.add(functionField, gbc);

        // built in functions box
        gbc.gridx = 2;
        gbc.gridy = 0;
        builtInFunctions = new JComboBox(BuiltInFunctions.getFunctions());
        builtInFunctions.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuiltInFunctions.Function function = (BuiltInFunctions.Function) builtInFunctions.getSelectedItem();
                functionField.setText(function.getFunction());
            }
        });
        panel.add(builtInFunctions, gbc);

        return panel;
    }
}

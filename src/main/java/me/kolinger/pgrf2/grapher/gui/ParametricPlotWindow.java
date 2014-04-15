package me.kolinger.pgrf2.grapher.gui;

import me.kolinger.pgrf2.grapher.graphics.ParametricPlot;
import me.kolinger.pgrf2.grapher.math.BuiltInFunctions;
import me.kolinger.pgrf2.grapher.math.BuiltInParametricPlots;
import me.kolinger.pgrf2.grapher.math.Calculator;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class ParametricPlotWindow extends BasePlotWindow {

    private ParametricPlot plot = new ParametricPlot();

    private JTextField xFunctionField;
    private JTextField yFunctionField;
    private JTextField zFunctionField;
    private JComboBox builtInPlots;

    private JTextField uFromField;
    private JTextField uToField;
    private JTextField uStepField;

    private JTextField vFromField;
    private JTextField vToField;
    private JTextField vStepField;

    public ParametricPlotWindow(MainWindow mainWindow) {
        super(mainWindow);
        BuiltInParametricPlots.Plot currentPlot = BuiltInParametricPlots.getPlots().get(0);
        plot.setXCalculator(new Calculator(currentPlot.getXFunction()));
        plot.setYCalculator(new Calculator(currentPlot.getYFunction()));
        plot.setZCalculator(new Calculator(currentPlot.getZFunction()));
        plot.setUFrom(currentPlot.getUFrom());
        plot.setUTo(currentPlot.getUTo());
        plot.setVFrom(currentPlot.getVFrom());
        plot.setVTo(currentPlot.getVTo());
        setPlot(plot);
        run();
    }

    protected Component buildTopControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // x function
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("X function: "), gbc);

        gbc.gridx = 1;
        xFunctionField = new JTextField(null, 65);
        xFunctionField.setText(plot.getXCalculator().getFunction());
        panel.add(xFunctionField, gbc);

        // y function
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Y function: "), gbc);

        gbc.gridx = 1;
        yFunctionField = new JTextField(null, 65);
        yFunctionField.setText(plot.getYCalculator().getFunction());
        panel.add(yFunctionField, gbc);

        // z function
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Z function: "), gbc);

        gbc.gridx = 1;
        zFunctionField = new JTextField(null, 65);
        zFunctionField.setText(plot.getZCalculator().getFunction());
        panel.add(zFunctionField, gbc);

        // built in plots box
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        builtInPlots = new JComboBox(BuiltInParametricPlots.getPlots());
        builtInPlots.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuiltInParametricPlots.Plot function = (BuiltInParametricPlots.Plot) builtInPlots.getSelectedItem();
                xFunctionField.setText(function.getXFunction());
                yFunctionField.setText(function.getYFunction());
                zFunctionField.setText(function.getZFunction());
                uFromField.setText(String.valueOf(function.getUFrom()));
                uToField.setText(String.valueOf(function.getUTo()));
                vFromField.setText(String.valueOf(function.getVFrom()));
                vToField.setText(String.valueOf(function.getVTo()));
            }
        });
        panel.add(builtInPlots, gbc);


        return panel;
    }

    protected Component buildSideControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // switch button
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        final JButton switchButton = new JButton("Switch to Z mode");
        switchButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                getMainWindow().switchToNormalMode();
            }
        });
        panel.add(switchButton, gbc);
        gbc.gridwidth = 1;

        // color
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Color: "), gbc);

        gbc.gridx = 1;
        colorBox = new JComboBox(new String[]{"Red", "Green", "Blue"});
        colorBox.setSelectedIndex(plot.getColor());
        panel.add(colorBox, gbc);

        // lines
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Lines: "), gbc);

        gbc.gridx = 1;
        linesCheckbox = new JCheckBox();
        linesCheckbox.setSelected(plot.isLinesEnabled());
        panel.add(linesCheckbox, gbc);

        // fill
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Fill: "), gbc);

        gbc.gridx = 1;
        fillCheckbox = new JCheckBox();
        fillCheckbox.setSelected(plot.isFillEnabled());
        panel.add(fillCheckbox, gbc);

        // light
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Light: "), gbc);

        gbc.gridx = 1;
        lightCheckbox = new JCheckBox();
        lightCheckbox.setSelected(plot.isLightEnabled());
        panel.add(lightCheckbox, gbc);

        // u from
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("U from: "), gbc);

        gbc.gridx = 1;
        uFromField = new JTextField();
        uFromField.setText(String.valueOf(plot.getUFrom()));
        panel.add(uFromField, gbc);

        // u to
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("U to: "), gbc);

        gbc.gridx = 1;
        uToField = new JTextField();
        uToField.setText(String.valueOf(plot.getUTo()));
        panel.add(uToField, gbc);

        // u step
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("U step: "), gbc);

        gbc.gridx = 1;
        uStepField = new JTextField();
        uStepField.setText(String.valueOf(plot.getUStep()));
        panel.add(uStepField, gbc);

        // v from
        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(new JLabel("V from: "), gbc);

        gbc.gridx = 1;
        vFromField = new JTextField();
        vFromField.setText(String.valueOf(plot.getVFrom()));
        panel.add(vFromField, gbc);

        // v to
        gbc.gridx = 0;
        gbc.gridy = 9;
        panel.add(new JLabel("V to: "), gbc);

        gbc.gridx = 1;
        vToField = new JTextField();
        vToField.setText(String.valueOf(plot.getVTo()));
        panel.add(vToField, gbc);

        // v step
        gbc.gridx = 0;
        gbc.gridy = 10;
        panel.add(new JLabel("V step: "), gbc);

        gbc.gridx = 1;
        vStepField = new JTextField();
        vStepField.setText(String.valueOf(plot.getVStep()));
        panel.add(vStepField, gbc);

        // apply button
        gbc.gridx = 0;
        gbc.gridy = 11;
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

                // functions
                if (xFunctionField.getText() != null) {
                    String function = xFunctionField.getText().trim();
                    if (function.length() > 0 &&
                            !function.equals(plot.getXCalculator().getFunction().trim())) {

                        Calculator calculator = new Calculator(function);
                        try {
                            calculator.calculateParametric(0, 0);
                            plot.setXCalculator(calculator);
                            plot.setNeedRefresh(true);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Error - X function is invalid",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }

                if (yFunctionField.getText() != null) {
                    String function = yFunctionField.getText().trim();
                    if (function.length() > 0 &&
                            !function.equals(plot.getYCalculator().getFunction().trim())) {

                        Calculator calculator = new Calculator(function);
                        try {
                            calculator.calculateParametric(0, 0);
                            plot.setYCalculator(calculator);
                            plot.setNeedRefresh(true);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Error - Y function is invalid",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }

                if (zFunctionField.getText() != null) {
                    String function = zFunctionField.getText().trim();
                    if (function.length() > 0 &&
                            !function.equals(plot.getZCalculator().getFunction().trim())) {

                        Calculator calculator = new Calculator(function);
                        try {
                            calculator.calculateParametric(0, 0);
                            plot.setZCalculator(calculator);
                            plot.setNeedRefresh(true);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Error - Z function is invalid",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }

                // ranges
                if (uFromField.getText() != null) {
                    try {
                        plot.setUFrom(Double.valueOf(uFromField.getText().trim()));
                        plot.setNeedRefresh(true);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error - U from must be valid number",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                if (uToField.getText() != null) {
                    try {
                        plot.setUTo(Double.valueOf(uToField.getText().trim()));
                        plot.setNeedRefresh(true);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error - U to must be valid number",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                if (uStepField.getText() != null) {
                    try {
                        plot.setUStep(Double.valueOf(uStepField.getText().trim()));
                        plot.setNeedRefresh(true);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error - U step must be valid number",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                if (vFromField.getText() != null) {
                    try {
                        plot.setVFrom(Double.valueOf(vFromField.getText().trim()));
                        plot.setNeedRefresh(true);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error - V from must be valid number",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                if (vToField.getText() != null) {
                    try {
                        plot.setVTo(Double.valueOf(vToField.getText().trim()));
                        plot.setNeedRefresh(true);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error - V to must be valid number",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                if (vStepField.getText() != null) {
                    try {
                        plot.setVStep(Double.valueOf(vStepField.getText().trim()));
                        plot.setNeedRefresh(true);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error - V step must be valid number",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        panel.add(applyButton, gbc);

        return panel;
    }
}

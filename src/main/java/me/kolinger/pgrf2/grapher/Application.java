package me.kolinger.pgrf2.grapher;

import me.kolinger.pgrf2.grapher.gui.MainWindow;

import javax.swing.SwingUtilities;

public class Application {

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        SwingUtilities.invokeLater(mainWindow);
    }
}

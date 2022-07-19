package com.motompro.tommogames.client.window;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public static final String DEFAULT_ERROR_MESSAGE = "Une erreur est survenue";
    public static final String COMMUNICATION_ERROR_MESSAGE = "Une erreur de communication avec le serveur est survenue";
    public static final String WRONG_CODE_ERROR_MESSAGE = "Ce code est invalide";

    private static final String TITLE = "TomMo Games";
    private static final Dimension DEFAULT_WINDOW_DIMENSION = new Dimension(1280, 720);

    public MainWindow() {
        init();
        this.setVisible(true);
    }

    private void init() {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        this.setTitle(TITLE);
        this.setSize(DEFAULT_WINDOW_DIMENSION);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void showPanel(JPanel panel) {
        this.setContentPane(panel);
        this.repaint();
        this.setVisible(true);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
    }

    public void showWarning(String title, String message) {
        JOptionPane.showMessageDialog(this,
                message,
                title,
                JOptionPane.WARNING_MESSAGE);
    }
}

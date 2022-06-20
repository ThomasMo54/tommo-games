package com.motompro.tommogames.client.window.panel;

import javax.swing.*;
import java.awt.*;

public class ConnectionPanel extends JPanel {

    private static final Font WELCOME_FONT = new Font("arial", Font.PLAIN, 36);
    private static final Font ENTER_NAME_FONT = new Font("arial", Font.PLAIN, 16);
    private static final Insets COMPONENTS_INSETS = new Insets(10, 0, 0, 0);
    private static final Dimension NAME_INPUT_DIMENSION = new Dimension(200, 24);
    private static final Dimension ENTER_BUTTON_DIMENSION = new Dimension(200, 32);

    public ConnectionPanel() {
        init();
    }

    private void init() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        JLabel welcomeLabel = new JLabel("Bienvenue !");
        welcomeLabel.setFont(WELCOME_FONT);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 0;
        this.add(welcomeLabel, constraints);
        JLabel enterNameLabel = new JLabel("Entrez votre nom");
        enterNameLabel.setFont(ENTER_NAME_FONT);
        constraints.gridy = 1;
        constraints.insets = COMPONENTS_INSETS;
        this.add(enterNameLabel, constraints);
        JTextField nameInput = new JTextField();
        nameInput.setPreferredSize(NAME_INPUT_DIMENSION);
        nameInput.addActionListener(event -> {
            nameValidated(nameInput.getText());
        });
        constraints.gridy = 2;
        this.add(nameInput, constraints);
        JButton enterButton = new JButton("Entrer");
        enterButton.setPreferredSize(ENTER_BUTTON_DIMENSION);
        enterButton.addActionListener(event -> {
            nameValidated(nameInput.getText());
        });
        constraints.gridy = 3;
        this.add(enterButton, constraints);
    }

    private void nameValidated(String name) {

    }
}

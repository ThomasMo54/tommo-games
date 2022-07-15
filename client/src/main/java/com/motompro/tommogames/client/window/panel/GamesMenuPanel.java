package com.motompro.tommogames.client.window.panel;

import com.motompro.tommogames.client.TomMoGames;

import javax.swing.*;
import java.awt.*;

public class GamesMenuPanel extends JPanel {

    public GamesMenuPanel() {
        init();
    }

    private void init() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Top bar
        JPanel topBarPanel = new JPanel();
        topBarPanel.setLayout(new GridBagLayout());
        topBarPanel.setBackground(Color.WHITE);
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.weightx = 1;
        constraints.weighty = 0.05;
        this.add(topBarPanel, constraints);
        JLabel usernameLabel = new JLabel("Bonjour " + TomMoGames.getInstance().getUsername());
        usernameLabel.setFont(new Font("verdana", Font.BOLD, 16));
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.insets = new Insets(0, 20 ,0, 0);
        topBarPanel.add(usernameLabel, constraints);

        // Games list
        JPanel gamesPanel = new JPanel();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 1;
        constraints.gridx = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        this.add(gamesPanel, constraints);
    }
}

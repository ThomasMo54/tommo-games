package com.motompro.tommogames.client.window.panel;

import com.motompro.tommogames.common.Game;

import javax.swing.*;
import java.awt.*;

public class WaitingRoomPanel extends JPanel {

    private final Game game;
    private final String code;

    public WaitingRoomPanel(Game game, String code) {
        this.game = game;
        this.code = code;
        init();
    }

    private void init() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        // Code
        JPanel codePanel = new JPanel(new GridLayout(1, 2));
        JLabel codeTitleLabel = new JLabel("Code : ");
        codeTitleLabel.setFont(new Font("verdana", Font.PLAIN, 16));
        codePanel.add(codeTitleLabel);
        JLabel codeLabel = new JLabel(code);
        codeLabel.setFont(new Font("verdana", Font.BOLD, 16));
        codePanel.add(codeLabel);
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.insets = new Insets(20, 20, 20, 0);
        constraints.weightx = 0;
        constraints.weighty = 0;
        this.add(codePanel, constraints);
    }
}

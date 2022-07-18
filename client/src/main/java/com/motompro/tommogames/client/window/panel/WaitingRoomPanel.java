package com.motompro.tommogames.client.window.panel;

import com.motompro.tommogames.common.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;

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
        JPanel codePanel = new JPanel(new GridLayout(1, 3));
        JLabel codeTitleLabel = new JLabel("Code : ");
        codeTitleLabel.setFont(new Font("verdana", Font.PLAIN, 16));
        codePanel.add(codeTitleLabel);
        JTextField codeLabel = new JTextField(code);
        codeLabel.setEditable(false);
        codeLabel.setBackground(null);
        codeLabel.setBorder(null);
        codeLabel.setFont(new Font("verdana", Font.BOLD, 16));
        codePanel.add(codeLabel);
        JButton copyButton = new JButton("Copier");
        copyButton.addActionListener(e -> {
            StringSelection selection = new StringSelection(code);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
        });
        codePanel.add(copyButton);
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.insets = new Insets(20, 20, 20, 0);
        constraints.weightx = 0;
        constraints.weighty = 0;
        this.add(codePanel, constraints);
    }
}

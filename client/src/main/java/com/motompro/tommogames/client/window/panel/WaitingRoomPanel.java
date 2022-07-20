package com.motompro.tommogames.client.window.panel;

import com.motompro.tommogames.client.GameClient;
import com.motompro.tommogames.client.TomMoGames;
import com.motompro.tommogames.client.WaitingRoom;
import com.motompro.tommogames.common.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class WaitingRoomPanel extends JPanel {

    private static final Font TITLE_FONT = new Font("verdana", Font.PLAIN, 16);
    private static final Font TITLE_VALUE_FONT = new Font("verdana", Font.BOLD, 16);
    private static final Dimension PLAYER_PANEL_DIMENSION = new Dimension(0, 60);

    private final WaitingRoom waitingRoom;

    private JLabel playerCountLabel;
    private JPanel playersPanel;

    public WaitingRoomPanel(WaitingRoom waitingRoom) {
        this.waitingRoom = waitingRoom;
        init();
    }

    private void init() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        // Left side panel
        JPanel leftSidePanel = new JPanel(new GridBagLayout());
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1;
        constraints.weightx = 0.25;
        this.add(leftSidePanel, constraints);
        // Game
        JPanel gamePanel = new JPanel(new GridLayout(1, 2));
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(20, 20, 10, 0);
        constraints.weighty = 0;
        leftSidePanel.add(gamePanel, constraints);
        JLabel gameTitleLabel = new JLabel("Jeu : ");
        gameTitleLabel.setFont(TITLE_FONT);
        gamePanel.add(gameTitleLabel);
        JLabel gameLabel = new JLabel(waitingRoom.getGame().getName());
        gameLabel.setFont(TITLE_VALUE_FONT);
        gamePanel.add(gameLabel);
        // Code
        JPanel codePanel = new JPanel(new GridLayout(1, 3));
        constraints.insets = new Insets(0, 20, 10, 0);
        constraints.gridy = 1;
        leftSidePanel.add(codePanel, constraints);
        JLabel codeTitleLabel = new JLabel("Code :");
        codeTitleLabel.setFont(TITLE_FONT);
        codePanel.add(codeTitleLabel);
        JTextField codeLabel = new JTextField(waitingRoom.getCode());
        codeLabel.setEditable(false);
        codeLabel.setBackground(null);
        codeLabel.setBorder(null);
        codeLabel.setFont(TITLE_VALUE_FONT);
        codePanel.add(codeLabel);
        JButton copyButton = new JButton("Copier");
        copyButton.addActionListener(e -> {
            StringSelection selection = new StringSelection(waitingRoom.getCode());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
        });
        codePanel.add(copyButton);
        // Player count
        JPanel playerCountPanel = new JPanel(new GridLayout(1, 2));
        constraints.insets = new Insets(0, 20, 20, 0);
        constraints.gridy = 2;
        leftSidePanel.add(playerCountPanel, constraints);
        JLabel playerCountTitleLabel = new JLabel("Joueurs : ");
        playerCountTitleLabel.setFont(TITLE_FONT);
        playerCountPanel.add(playerCountTitleLabel);
        this.playerCountLabel = new JLabel("0 / " + waitingRoom.getGame().getMaxPlayers());
        playerCountLabel.setFont(TITLE_VALUE_FONT);
        playerCountPanel.add(playerCountLabel);
        // Player list
        JPanel listContentPanel = new JPanel(new BorderLayout());
        this.playersPanel = new JPanel(new GridBagLayout());
        playersPanel.setBackground(Color.WHITE);
        listContentPanel.add(playersPanel, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(listContentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 3;
        constraints.weighty = 1;
        leftSidePanel.add(scrollPane, constraints);

        // Right side panel
        JPanel rightSidePanel = new JPanel(new GridBagLayout());
        constraints.anchor = GridBagConstraints.NORTHEAST;
        constraints.gridy = 0;
        constraints.weighty = 1;
        constraints.weightx = 1;
        this.add(rightSidePanel, constraints);
        // Buttons panel
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        constraints.anchor = GridBagConstraints.EAST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        rightSidePanel.add(buttonsPanel);
        JButton leaveButton = new JButton("Quitter");
        leaveButton.setPreferredSize(new Dimension(100, 40));
        leaveButton.addActionListener(e -> {
            GameClient client = TomMoGames.getInstance().getClient();
            client.removeServerListener(waitingRoom);
            TomMoGames.getInstance().getMainWindow().showPanel(new GamesMenuPanel());
            try {
                client.sendMessage("waitingRoom leave");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        buttonsPanel.add(leaveButton, constraints);
        // Rules panel
        JPanel rulesPanel = new JPanel(new GridBagLayout());
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 1;
        rightSidePanel.add(rulesPanel);
    }

    public void updatePlayerList(Map<UUID, String> players) {
        playerCountLabel.setText(players.size() + " / " + waitingRoom.getGame().getMaxPlayers());
        playersPanel.removeAll();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.weighty = 0;
        AtomicInteger atomicGridY = new AtomicInteger();
        players.forEach((playerUuid, playerName) -> {
            JPanel playerPanel = new JPanel(new GridBagLayout());
            playerPanel.setBackground(Color.WHITE);
            playerPanel.setPreferredSize(PLAYER_PANEL_DIMENSION);
            constraints.anchor = GridBagConstraints.FIRST_LINE_START;
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridy = atomicGridY.getAndIncrement();
            playersPanel.add(playerPanel, constraints);
            JLabel nameLabel = new JLabel(playerName);
            constraints.anchor = GridBagConstraints.WEST;
            constraints.fill = GridBagConstraints.NONE;
            constraints.insets = new Insets(0 ,20, 0, 0);
            constraints.gridy = 0;
            playerPanel.add(nameLabel, constraints);
        });
        this.revalidate();
    }
}

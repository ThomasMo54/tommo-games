package com.motompro.tommogames.client.window.panel;

import com.motompro.tommogames.client.GameClient;
import com.motompro.tommogames.client.TomMoGames;
import com.motompro.tommogames.client.waitingRoom.WaitingRoom;
import com.motompro.tommogames.client.window.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class WaitingRoomPanel extends JPanel {

    private static final Font TITLE_FONT = new Font("verdana", Font.PLAIN, 16);
    private static final Font TITLE_VALUE_FONT = new Font("verdana", Font.BOLD, 16);
    private static final Color PLAYER_PANEL_COLOR_1 = new Color(240, 240, 240);
    private static final Color PLAYER_PANEL_COLOR_2 = new Color(225, 225, 225);

    private final WaitingRoom waitingRoom;

    private JLabel playerCountLabel;
    private JPanel playersPanel;
    private JButton startButton;

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
        constraints.weightx = 0.15;
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
        JLabel gameLabel = new JLabel(waitingRoom.getGameData().getName());
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
        this.playerCountLabel = new JLabel("0 / " + waitingRoom.getGameData().getMaxPlayers());
        playerCountLabel.setFont(TITLE_VALUE_FONT);
        playerCountPanel.add(playerCountLabel);
        // Player list
        JPanel listContentPanel = new JPanel(new BorderLayout());
        listContentPanel.setBackground(Color.WHITE);
        this.playersPanel = new JPanel(new GridBagLayout());
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
        // Rules panel
        JPanel rulesPanel = new JPanel(new GridBagLayout());
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(20, 20, 0, 20);
        rightSidePanel.add(rulesPanel, constraints);
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(0, 0, 10, 0);
        constraints.weighty = 0;
        AtomicInteger atomicGridY = new AtomicInteger();
        waitingRoom.getRulePanels().values().forEach(rulePanel -> {
            constraints.gridy = atomicGridY.getAndIncrement();
            rulesPanel.add(rulePanel, constraints);
        });
        // Buttons panel
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(20, 20, 20, 20);
        constraints.gridy = 1;
        rightSidePanel.add(buttonsPanel, constraints);
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
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridy = 0;
        constraints.weightx = 0;
        buttonsPanel.add(leaveButton, constraints);
        if(!waitingRoom.isOwner())
            return;
        this.startButton = new JButton("Commencer");
        startButton.setEnabled(waitingRoom.getGameData().getMinPlayers() == 1);
        startButton.setPreferredSize(new Dimension(130, 40));
        startButton.addActionListener(e -> {
            waitingRoom.startGame();
        });
        constraints.gridx = 1;
        buttonsPanel.add(startButton, constraints);
    }

    public void updatePlayerList(Map<UUID, String> players) {
        playerCountLabel.setText(players.size() + " / " + waitingRoom.getGameData().getMaxPlayers());
        if(startButton != null)
            startButton.setEnabled(players.size() >= waitingRoom.getGameData().getMinPlayers());
        playersPanel.removeAll();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 0;
        AtomicInteger atomicGridY = new AtomicInteger();
        players.forEach((playerUuid, playerName) -> {
            boolean hasKickButton = waitingRoom.isOwner() && !playerUuid.equals(TomMoGames.getInstance().getUuid());
            PlayerPanel playerPanel = new PlayerPanel(playerUuid, playerName, hasKickButton);
            playerPanel.setBackground(atomicGridY.get() % 2 == 0 ? PLAYER_PANEL_COLOR_1 : PLAYER_PANEL_COLOR_2);
            constraints.gridy = atomicGridY.getAndIncrement();
            playersPanel.add(playerPanel, constraints);
        });
        this.revalidate();
    }
}

class PlayerPanel extends JPanel {

    private static final Dimension DIMENSION = new Dimension(0, 60);

    private final UUID uuid;
    private final String name;
    private final boolean hasKickButton;

    public PlayerPanel(UUID uuid, String name, boolean hasKickButton) {
        this.uuid = uuid;
        this.name = name;
        this.hasKickButton = hasKickButton;
        init();
    }

    private void init() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        this.setPreferredSize(DIMENSION);
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("verdana", Font.PLAIN, 18));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(0 ,20, 0, 0);
        constraints.weightx = 1;
        this.add(nameLabel, constraints);
        if(!hasKickButton)
            return;
        JButton kickButton = new JButton("Exclure");
        kickButton.addActionListener(e -> {
            try {
                TomMoGames.getInstance().getClient().sendMessage("waitingRoom kick " + uuid);
            } catch (IOException ex) {
                TomMoGames.getInstance().getMainWindow().showError(MainWindow.DEFAULT_ERROR_MESSAGE);
            }
        });
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(0 ,0, 0, 20);
        constraints.gridx = 1;
        this.add(kickButton, constraints);
    }
}

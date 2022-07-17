package com.motompro.tommogames.client.window.panel;

import com.motompro.tommogames.client.TomMoGames;
import com.motompro.tommogames.common.Game;
import com.motompro.tommogames.common.GameRegistry;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.atomic.AtomicInteger;

public class GamesMenuPanel extends JPanel {

    private static final double TOP_BAR_HEIGHT_PERC = 0.05;
    private static final Insets GAME_PANEL_INSETS = new Insets(30, 20, 0, 20);

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
        constraints.weighty = TOP_BAR_HEIGHT_PERC;
        this.add(topBarPanel, constraints);
        JLabel usernameLabel = new JLabel("Bonjour " + TomMoGames.getInstance().getUsername());
        usernameLabel.setFont(new Font("verdana", Font.BOLD, 16));
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.insets = new Insets(0, 20 ,0, 0);
        topBarPanel.add(usernameLabel, constraints);

        // Games list
        JPanel listContentPanel = new JPanel(new BorderLayout());
        JPanel gamesPanel = new JPanel(new GridBagLayout());
        listContentPanel.add(gamesPanel, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(listContentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 1;
        constraints.gridx = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        this.add(scrollPane, constraints);
        constraints.insets = GAME_PANEL_INSETS;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weighty = 0;
        AtomicInteger atomicGridY = new AtomicInteger();
        GameRegistry.getGames().values().forEach(game -> {
            constraints.gridy = atomicGridY.getAndIncrement();
            gamesPanel.add(new GamePanel(game), constraints);
        });
    }
}

class GamePanel extends JPanel implements MouseInputListener {

    private final Game game;
    private JButton hostButton, joinButton;

    public GamePanel(Game game) {
        this.game = game;
        init();
    }

    private void init() {
        this.setBackground(Color.WHITE);
        this.addMouseListener(this);
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        // Info
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBackground(Color.WHITE);
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.insets = new Insets(15, 15, 15, 0);
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.weightx = 1;
        this.add(infoPanel, constraints);
        JLabel nameLabel = new JLabel(game.getName());
        nameLabel.setFont(new Font("verdana", Font.PLAIN, 12));
        infoPanel.add(nameLabel);
        String playersNumber = game.getMinPlayers() == game.getMaxPlayers() ? String.valueOf(game.getMaxPlayers()) : game.getMinPlayers() + "-" + game.getMaxPlayers();
        String playersText = playersNumber + " Joueur" + (game.getMaxPlayers() > 1 ? "s" : "");
        JLabel playersLabel = new JLabel(playersText);
        playersLabel.setForeground(Color.GRAY);
        infoPanel.add(playersLabel);
        // Buttons
        this.hostButton = new JButton("Héberger");
        hostButton.setVisible(false);
        hostButton.addMouseListener(this);
        hostButton.addActionListener(e -> {

        });
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(0, 0, 0, 15);
        constraints.gridx = 1;
        constraints.weightx = 0;
        this.add(hostButton, constraints);
        this.joinButton = new JButton("Rejoindre");
        joinButton.setVisible(false);
        joinButton.addMouseListener(this);
        joinButton.addActionListener(e -> {

        });
        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 2;
        this.add(joinButton, constraints);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        hostButton.setVisible(true);
        joinButton.setVisible(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        hostButton.setVisible(false);
        joinButton.setVisible(false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {}
}
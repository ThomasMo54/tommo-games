package com.motompro.tommogames.client.window.panel;

import com.motompro.tommogames.client.TomMoGames;
import com.motompro.tommogames.client.WaitingRoom;
import com.motompro.tommogames.client.window.MainWindow;
import com.motompro.tommogames.common.Game;
import com.motompro.tommogames.common.GameRegistry;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
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

    private static final Dimension DEFAULT_DIMENSION = new Dimension(0, 60);
    private static final Dimension HOVER_DIMENSION = new Dimension(0, 80);
    private static final Dimension BUTTON_DIMENSION = new Dimension(100, 45);

    private final Game game;
    private JButton createButton, joinButton;

    public GamePanel(Game game) {
        this.game = game;
        init();
    }

    private void init() {
        this.setPreferredSize(DEFAULT_DIMENSION);
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
        this.createButton = new JButton("Créer");
        createButton.setPreferredSize(BUTTON_DIMENSION);
        createButton.setVisible(false);
        createButton.addMouseListener(this);
        createButton.addActionListener(e -> {
            createGame();
        });
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(0, 0, 0, 15);
        constraints.gridx = 1;
        constraints.weightx = 0;
        this.add(createButton, constraints);
        this.joinButton = new JButton("Rejoindre");
        joinButton.setPreferredSize(BUTTON_DIMENSION);
        joinButton.setVisible(false);
        joinButton.addMouseListener(this);
        joinButton.addActionListener(e -> {
            joinGame();
        });
        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 2;
        this.add(joinButton, constraints);
    }

    private void createGame() {
        new WaitingRoom(game, true);
        try {
            TomMoGames.getInstance().getClient().sendMessage("waitingRoom create " + game.getId());
        } catch (IOException e) {
            MainWindow window = TomMoGames.getInstance().getMainWindow();
            window.showError(MainWindow.COMMUNICATION_ERROR_MESSAGE);
            window.showPanel(new GamesMenuPanel());
        }
    }

    private void joinGame() {
        MainWindow window = TomMoGames.getInstance().getMainWindow();
        String code = JOptionPane.showInputDialog(window, "Veuillez entrer le code de la partie", "Code", JOptionPane.INFORMATION_MESSAGE);
        if(code == null) {
            window.showError(MainWindow.WRONG_CODE_ERROR_MESSAGE);
            return;
        }
        new WaitingRoom(game, false);
        try {
            TomMoGames.getInstance().getClient().sendMessage("waitingRoom join " + code);
        } catch (IOException e) {
            window.showError(MainWindow.COMMUNICATION_ERROR_MESSAGE);
            window.showPanel(new GamesMenuPanel());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.setPreferredSize(HOVER_DIMENSION);
        createButton.setVisible(true);
        joinButton.setVisible(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setPreferredSize(DEFAULT_DIMENSION);
        createButton.setVisible(false);
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
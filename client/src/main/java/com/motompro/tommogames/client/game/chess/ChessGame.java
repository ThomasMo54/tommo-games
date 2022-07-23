package com.motompro.tommogames.client.game.chess;

import com.motompro.tommogames.client.TomMoGames;
import com.motompro.tommogames.client.game.Game;
import com.motompro.tommogames.client.game.chess.panel.BoardPanel;
import com.motompro.tommogames.client.window.MainWindow;
import com.motompro.tommogames.common.GameData;
import com.motompro.tommogames.common.GameRules;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Set;

public class ChessGame extends Game<ChessPlayer> {

    private final BoardPanel boardPanel;

    public ChessGame(GameData data, GameRules rules, Set<ChessPlayer> players) {
        super(data, rules, players);
        this.boardPanel = new BoardPanel();
        initComponents();
    }

    private void initComponents() {
        boardPanel.setBackground(Color.RED);
        JPanel panel = new JPanel();
        panel.add(boardPanel);
        MainWindow window = TomMoGames.getInstance().getMainWindow();
        window.showPanel(panel);
        panel.revalidate();
        setBoardSize(panel.getSize());
        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                panel.revalidate();
                setBoardSize(panel.getSize());
            }
        });
        window.addWindowStateListener(e -> setBoardSize(panel.getSize()));
        window.revalidate();
    }

    private void setBoardSize(Dimension windowSize) {
        int smallestSideLength = (int) Math.min(windowSize.getWidth(), windowSize.getHeight());
        boardPanel.setPreferredSize(new Dimension(smallestSideLength, smallestSideLength));
    }
}

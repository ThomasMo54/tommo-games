package com.motompro.tommogames.client.game.chess;

import com.motompro.tommogames.client.TomMoGames;
import com.motompro.tommogames.client.game.Game;
import com.motompro.tommogames.client.game.chess.panel.BoardPanel;
import com.motompro.tommogames.client.game.chess.piece.Piece;
import com.motompro.tommogames.client.window.MainWindow;
import com.motompro.tommogames.common.GameData;
import com.motompro.tommogames.common.GameRules;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Map;
import java.util.UUID;

public class ChessGame extends Game<ChessPlayer> {

    public static final int BOARD_SIZE = 8;

    private final boolean whiteSide;
    private final BoardPanel boardPanel;
    private Piece[][] pieces;

    public ChessGame(GameData data, GameRules rules, Map<UUID, ChessPlayer> players) {
        super(data, rules, players);
        this.whiteSide = players.get(TomMoGames.getInstance().getUuid()).isWhite();
        this.boardPanel = new BoardPanel(whiteSide);
        this.pieces = new Piece[BOARD_SIZE][BOARD_SIZE];
        initGame();
        initComponents();
    }

    private void initGame() {
        // Init pieces
        
    }

    private void initComponents() {
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

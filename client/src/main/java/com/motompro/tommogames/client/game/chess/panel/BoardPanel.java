package com.motompro.tommogames.client.game.chess.panel;

import com.motompro.tommogames.client.game.chess.ChessGame;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    private static final Color LIGHT_SQUARE_COLOR = new Color(235, 168, 52);
    private static final Color DARK_SQUARE_COLOR = new Color(158, 105, 25);

    private final boolean whiteSide;

    public BoardPanel(boolean whiteSide) {
        this.whiteSide = whiteSide;
    }

    @Override
    public void paint(Graphics g) {
        Dimension size = this.getSize();
        int squareSize = (int) (size.getWidth() / ChessGame.BOARD_SIZE);
        // Draw board
        for(int y = 0; y < ChessGame.BOARD_SIZE; y++)
            for(int x = 0; x < ChessGame.BOARD_SIZE; x++) {
                g.setColor((x + y) % 2 == 0 ? LIGHT_SQUARE_COLOR : DARK_SQUARE_COLOR);
                g.fillRect(x * squareSize, y * squareSize, squareSize, squareSize);
            }
        // Draw pieces

    }
}

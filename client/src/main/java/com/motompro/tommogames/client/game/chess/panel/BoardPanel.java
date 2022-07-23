package com.motompro.tommogames.client.game.chess.panel;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    private static final Color LIGHT_SQUARE_COLOR = new Color(235, 168, 52);
    private static final Color DARK_SQUARE_COLOR = new Color(158, 105, 25);

    @Override
    public void paint(Graphics g) {
        Dimension size = this.getSize();
        int squareSize = (int) (size.getWidth() / 8);
        // Draw board
        for(int y = 0; y < 8; y++)
            for(int x = 0; x < 8; x++) {
                g.setColor((x + y) % 2 == 0 ? LIGHT_SQUARE_COLOR : DARK_SQUARE_COLOR);
                g.fillRect(x * squareSize, y * squareSize, squareSize, squareSize);
            }
    }
}

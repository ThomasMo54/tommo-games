package com.motompro.tommogames.client.game.chess.piece;

import java.awt.*;

public abstract class Piece {

    private final boolean white;

    public Piece(boolean white) {
        this.white = white;
    }

    public boolean isWhite() {
        return white;
    }

    public abstract Image getImage();
}

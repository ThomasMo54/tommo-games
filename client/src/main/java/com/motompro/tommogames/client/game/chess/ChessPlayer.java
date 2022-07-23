package com.motompro.tommogames.client.game.chess;

import com.motompro.tommogames.client.game.Player;

import java.util.UUID;

public class ChessPlayer extends Player {

    private final boolean white;

    public ChessPlayer(UUID uuid, String name, boolean white) {
        super(uuid, name);
        this.white = white;
    }

    public boolean isWhite() {
        return white;
    }
}

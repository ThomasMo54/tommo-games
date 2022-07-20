package com.motompro.tommogames.server.room;

import com.motompro.tommogames.common.Game;
import com.motompro.tommogames.common.GameRegistry;
import com.motompro.tommogames.server.GameClient;

public class ChessRoom extends GameRoom {

    private final Game game;

    public ChessRoom(String code, GameClient owner) {
        super(code, owner);
        this.game = GameRegistry.getGames().get(GameRegistry.CHESS_ID);
    }

    @Override
    public Game getGame() {
        return game;
    }
}

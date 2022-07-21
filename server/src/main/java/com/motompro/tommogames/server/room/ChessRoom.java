package com.motompro.tommogames.server.room;

import com.motompro.tommogames.common.GameData;
import com.motompro.tommogames.common.GameRegistry;
import com.motompro.tommogames.server.GameClient;

public class ChessRoom extends GameRoom {

    private final GameData gameData;

    public ChessRoom(String code, GameClient owner) {
        super(code, owner);
        this.gameData = GameRegistry.getGames().get(GameRegistry.CHESS_ID);
    }

    @Override
    public GameData getGame() {
        return gameData;
    }
}

package com.motompro.tommogames.server.room;

import com.motompro.tommogames.common.GameData;
import com.motompro.tommogames.common.GameRegistry;
import com.motompro.tommogames.server.GameClient;

public class ChessRoom extends GameRoom {

    private static final GameData GAME_DATA = GameRegistry.getGames().get(GameRegistry.CHESS_ID);

    public ChessRoom(String code, GameClient owner) {
        super(code, owner);
    }

    @Override
    public GameData getGameData() {
        return GAME_DATA;
    }
}

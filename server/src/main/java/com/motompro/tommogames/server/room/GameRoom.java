package com.motompro.tommogames.server.room;

import com.motompro.tcplib.server.Room;
import com.motompro.tommogames.common.GameData;
import com.motompro.tommogames.common.GameRules;
import com.motompro.tommogames.server.GameClient;

public abstract class GameRoom extends Room<GameClient> {

    private final String code;
    private final GameClient owner;
    private final GameRules rules;

    public GameRoom(String code, GameClient owner) {
        this.code = code;
        this.owner = owner;
        this.rules = getGameData().getDefaultRules();
    }

    public String getCode() {
        return code;
    }

    public GameClient getOwner() {
        return owner;
    }

    public GameRules getRules() {
        return rules;
    }

    public abstract GameData getGameData();
}

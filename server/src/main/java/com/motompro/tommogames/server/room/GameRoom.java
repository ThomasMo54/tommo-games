package com.motompro.tommogames.server.room;

import com.motompro.tcplib.server.Room;
import com.motompro.tommogames.server.GameClient;

public class GameRoom extends Room<GameClient> {

    private final String code;
    private final GameClient owner;

    public GameRoom(String code, GameClient owner) {
        this.code = code;
        this.owner = owner;
    }

    public String getCode() {
        return code;
    }

    public GameClient getOwner() {
        return owner;
    }
}

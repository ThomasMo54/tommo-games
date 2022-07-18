package com.motompro.tommogames.server.room;

import com.motompro.tcplib.server.Room;
import com.motompro.tommogames.server.GameClient;

public class GameRoom extends Room<GameClient> {

    private static final int CODE_LENGTH = 6;

    private final String code;

    public GameRoom(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

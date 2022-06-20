package com.motompro.tommogames.server;

import java.io.IOException;

public class TomMoGames {

    private static TomMoGames instance;
    private static final int SERVER_PORT = 54150;

    private final GameServer server;

    protected TomMoGames() {
        try {
            this.server = new GameServer(SERVER_PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        instance = new TomMoGames();
    }
}

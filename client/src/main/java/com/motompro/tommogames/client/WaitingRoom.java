package com.motompro.tommogames.client;

import com.motompro.tcplib.client.ServerListener;
import com.motompro.tommogames.common.Game;

public class WaitingRoom implements ServerListener {

    private final Game game;

    public WaitingRoom(Game game) {
        this.game = game;
        TomMoGames.getInstance().getClient().addServerListener(this);
    }

    @Override
    public void onServerMessage(String message) {

    }

    @Override
    public void onServerDisconnect() {}
}

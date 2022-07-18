package com.motompro.tommogames.client;

import com.motompro.tcplib.client.ServerListener;
import com.motompro.tommogames.client.window.panel.WaitingRoomPanel;
import com.motompro.tommogames.common.Game;

public class WaitingRoom implements ServerListener {

    private final Game game;
    private final WaitingRoomPanel panel;

    public WaitingRoom(Game game) {
        this.game = game;
        this.panel = new WaitingRoomPanel(game);
        TomMoGames.getInstance().getClient().addServerListener(this);
        TomMoGames.getInstance().getMainWindow().showPanel(panel);
    }

    @Override
    public void onServerMessage(String message) {

    }

    @Override
    public void onServerDisconnect() {}
}

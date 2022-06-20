package com.motompro.tommogames.client;

import com.motompro.tcplib.client.Client;
import com.motompro.tcplib.client.ServerListener;

import java.io.IOException;

public class GameClient extends Client implements ServerListener {

    public GameClient(String ip, int port) throws IOException {
        super(ip, port);
        this.addServerListener(this);
    }

    @Override
    public void onServerDisconnect() {

    }

    @Override
    public void onServerMessage(String message) {

    }
}

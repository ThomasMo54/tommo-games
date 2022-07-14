package com.motompro.tommogames.server;

import com.motompro.tcplib.server.ClientListener;
import com.motompro.tcplib.server.Server;
import com.motompro.tcplib.server.ServerSideClient;

import java.io.IOException;

public class GameServer extends Server<GameClient> implements ClientListener {

    public GameServer(int port) throws IOException {
        super(port);
        this.addClientListener(this);
    }

    @Override
    public GameClient generateClient(ServerSideClient serverSideClient) {
        return (GameClient) serverSideClient;
    }

    @Override
    public void onClientConnect(ServerSideClient client) {

    }

    @Override
    public void onClientDisconnect(ServerSideClient client) {

    }

    @Override
    public void onClientMessage(ServerSideClient client, String message) {

    }
}

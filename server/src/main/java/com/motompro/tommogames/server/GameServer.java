package com.motompro.tommogames.server;

import com.motompro.tcplib.server.ClientListener;
import com.motompro.tcplib.server.Server;
import com.motompro.tcplib.server.ServerSideClient;

import java.io.IOException;

public class GameServer extends Server<GameClient> implements ClientListener<GameClient> {

    public GameServer(int port) throws IOException {
        super(port);
        this.addClientListener(this);
    }

    @Override
    protected GameClient generateClient(ServerSideClient serverSideClient) {
        try {
            return new GameClient(serverSideClient.getUuid(), serverSideClient.getSocket());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onClientConnect(GameClient client) {

    }

    @Override
    public void onClientDisconnect(GameClient client) {

    }

    @Override
    public void onClientMessage(GameClient client, String message) {
        String[] splitMessage = message.split(" ");
        switch(splitMessage[0]) {
            case "CONNECTION": {
                if(splitMessage.length == 1) {
                    try {
                        client.sendMessage("CONNECTION error");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }
                clientConnected(client, splitMessage[1]);
                break;
            }
        }
    }

    private void clientConnected(GameClient client, String name) {
        client.setName(name);
        try {
            client.sendMessage("CONNECTION success");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

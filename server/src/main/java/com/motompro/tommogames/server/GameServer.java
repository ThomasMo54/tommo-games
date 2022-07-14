package com.motompro.tommogames.server;

import com.motompro.tcplib.server.ClientListener;
import com.motompro.tcplib.server.Server;
import com.motompro.tcplib.server.ServerSideClient;

import java.io.IOException;

public class GameServer extends Server<GameClient> implements ClientListener<GameClient> {

    public GameServer(int port) throws IOException {
        super(port);
        this.addClientListener(this);
        Logger.log("Server started");
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
        Logger.log("Client " + client.getUuid() + " connected");
    }

    @Override
    public void onClientDisconnect(GameClient client) {
        Logger.log("Client " + client.getUuid() + " disconnected");
    }

    @Override
    public void onClientMessage(GameClient client, String message) {
        String[] splitMessage = message.split(" ");
        switch(splitMessage[0]) {
            case "LOG": {
                if(splitMessage.length == 1) {
                    try {
                        client.sendMessage("LOG error");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }
                clientLog(client, splitMessage[1]);
                break;
            }
        }
    }

    private void clientLog(GameClient client, String name) {
        client.setName(name);
        try {
            client.sendMessage("LOG success");
            Logger.log("Client " + client.getUuid() + " logged with name " + name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

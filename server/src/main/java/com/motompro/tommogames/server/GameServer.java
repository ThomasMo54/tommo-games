package com.motompro.tommogames.server;

import com.motompro.tcplib.server.ClientListener;
import com.motompro.tcplib.server.Server;
import com.motompro.tcplib.server.ServerSideClient;
import com.motompro.tommogames.common.Game;
import com.motompro.tommogames.common.GameRegistry;
import com.motompro.tommogames.server.room.ChessRoom;
import com.motompro.tommogames.server.room.GameRoom;

import java.io.IOException;
import java.util.Random;

public class GameServer extends Server<GameClient> implements ClientListener<GameClient> {

    private static final int CODE_LENGTH = 6;

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
            case "log": {
                handleLog(client, splitMessage);
                break;
            }
            case "waitingRoom": {
                handleWaitingRoom(client, splitMessage);
                break;
            }
        }
    }

    private void handleLog(GameClient client, String[] splitMessage) {
        if(splitMessage.length == 1) {
            try {
                client.sendMessage("log error");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        client.setName(splitMessage[1]);
        try {
            client.sendMessage("log success");
            Logger.log("Client " + client.getUuid() + " logged with name " + splitMessage[1]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleWaitingRoom(GameClient client, String[] splitMessage) {
        if(splitMessage.length < 2)
            return;
        switch(splitMessage[1]) {
            case "create": {
                if(splitMessage.length < 3)
                    return;
                createRoom(GameRegistry.getGames().get(splitMessage[2]), client);
            }
        }
    }

    private void createRoom(Game game, GameClient owner) {
        // Generate random new code
        StringBuilder codeBuilder = new StringBuilder();
        do {
            codeBuilder.setLength(0);
            codeBuilder.append(String.format("%0" + CODE_LENGTH + "d", new Random().nextInt((int) Math.pow(10, CODE_LENGTH))));
        } while(getRooms().values().stream().anyMatch(room -> ((GameRoom) room).getCode().equals(codeBuilder.toString())));
        String code = codeBuilder.toString();
        GameRoom room;
        switch(game.getId()) {
            case GameRegistry.CHESS_ID:
                room = new ChessRoom(code);
                break;
            default: return;
        }
        room.addClient(owner);
        addRoom(room);
        try {
            owner.sendMessage("waitingRoom code " + code);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

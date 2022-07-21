package com.motompro.tommogames.server;

import com.motompro.tcplib.server.ClientListener;
import com.motompro.tcplib.server.Server;
import com.motompro.tcplib.server.ServerSideClient;
import com.motompro.tommogames.common.GameData;
import com.motompro.tommogames.common.GameRegistry;
import com.motompro.tommogames.server.room.ChessRoom;
import com.motompro.tommogames.server.room.GameRoom;

import java.io.IOException;
import java.util.*;

public class GameServer extends Server<GameClient> implements ClientListener<GameClient> {

    private static final int CODE_LENGTH = 4;

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
        client.getRoom().ifPresent(room -> {
            if(!(room instanceof GameRoom))
                return;
            GameRoom gameRoom = (GameRoom) room;
            if(gameRoom.getOwner().getUuid().equals(client.getUuid()))
               closeRoom(gameRoom);
        });
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
                handleWaitingRoom(client, splitMessage, message);
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
            client.sendMessage("log success " + client.getUuid());
            Logger.log("Client " + client.getUuid() + " logged with name " + splitMessage[1]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleWaitingRoom(GameClient client, String[] splitMessage, String message) {
        if(splitMessage.length < 2)
            return;
        switch(splitMessage[1]) {
            case "create": {
                if(splitMessage.length < 3)
                    return;
                createRoom(GameRegistry.getGames().get(splitMessage[2]), client);
                break;
            }
            case "join": {
                if(splitMessage.length < 3)
                    return;
                joinRoom(splitMessage[2], client);
                break;
            }
            case "leave": {
                if(!client.getRoom().isPresent() || !(client.getRoom().get() instanceof GameRoom))
                    return;
                GameRoom room = (GameRoom) client.getRoom().get();
                room.removeClient(client);
                if(client.getUuid().equals(room.getOwner().getUuid())) {
                    closeRoom(room);
                    return;
                }
                sendRoomPlayerList(room);
                break;
            }
            case "kick": {
                if(splitMessage.length < 3 || !client.getRoom().isPresent() || !(client.getRoom().get() instanceof GameRoom))
                    return;
                kickRoom((GameRoom) client.getRoom().get(), getClients().get(UUID.fromString(splitMessage[2])));
                break;
            }
            case "rules": {
                if(splitMessage.length < 4 || !client.getRoom().isPresent() || !(client.getRoom().get() instanceof GameRoom))
                    return;
                GameRoom room = (GameRoom) client.getRoom().get();
                room.broadcast(message);
                for(int i = 2; i < splitMessage.length; i += 2)
                    room.getRules().set(splitMessage[i], splitMessage[i + 1]);
                break;
            }
            case "start": {
                if(!client.getRoom().isPresent() || !(client.getRoom().get() instanceof GameRoom))
                    return;
                GameRoom room = (GameRoom) client.getRoom().get();
                room.broadcast(createRoomRulesString(room));
                room.broadcast("waitingRoom start");
                break;
            }
        }
    }

    private void createRoom(GameData gameData, GameClient owner) {
        // Generate random new code
        StringBuilder codeBuilder = new StringBuilder();
        do {
            codeBuilder.setLength(0);
            codeBuilder.append(String.format("%0" + CODE_LENGTH + "d", new Random().nextInt((int) Math.pow(10, CODE_LENGTH))));
        } while(getRooms().values().stream().anyMatch(room -> ((GameRoom) room).getCode().equals(codeBuilder.toString())));
        String code = codeBuilder.toString();
        GameRoom room;
        switch(gameData.getId()) {
            case GameRegistry.CHESS_ID:
                room = new ChessRoom(code, owner);
                break;
            default: return;
        }
        room.addClient(owner);
        Logger.log("Room " + room.getUuid() + " (" + gameData.getName() + ") created with code " + code + " by client " + owner.getUuid());
        addRoom(room);
        try {
            owner.sendMessage("waitingRoom joinSuccess " + code);
            if(!owner.getName().isPresent())
                return;
            owner.sendMessage("waitingRoom playerList " + owner.getUuid() + " " + owner.getName().get());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void joinRoom(String code, GameClient client) {
        Optional<GameRoom> roomOpt = getRooms().values().stream().filter(room -> room instanceof GameRoom && ((GameRoom) room).getCode().equals(code)).findFirst().map(room -> (GameRoom) room);
        try {
            if(!roomOpt.isPresent()) {
                client.sendMessage("waitingRoom wrongCode");
                return;
            }
            GameRoom room = roomOpt.get();
            if(room.getClientNumber() >= room.getGameData().getMaxPlayers()) {
                client.sendMessage("waitingRoom full");
                return;
            }
            room.addClient(client);
            Logger.log("Client " + client.getUuid() + " joined room " + room.getUuid());
            client.sendMessage("waitingRoom joinSuccess " + code);
            sendRoomPlayerList(room);
            client.sendMessage(createRoomRulesString(room));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendRoomPlayerList(GameRoom room) {
        StringBuilder playerListStringBuilder = new StringBuilder();
        playerListStringBuilder.append("waitingRoom playerList");
        room.getClients().stream()
                .filter(gameClient -> gameClient.getName().isPresent())
                .forEach(gameClient -> {
                    playerListStringBuilder.append(" ").append(gameClient.getUuid()).append(" ").append(gameClient.getName().get());
                });
        room.broadcast(playerListStringBuilder.toString());
    }

    private String createRoomRulesString(GameRoom room) {
        StringBuilder rulesBuilder = new StringBuilder();
        rulesBuilder.append("waitingRoom rules");
        room.getRules().getAll().forEach((rule, value) -> {
            rulesBuilder.append(" ").append(rule).append(" ").append(value);
        });
        return rulesBuilder.toString();
    }

    private void closeRoom(GameRoom room) {
        room.broadcast("waitingRoom kick");
        room.removeClients(room.getClients());
    }

    private void kickRoom(GameRoom room, GameClient kickedClient) {
        if(kickedClient == null)
            return;
        room.removeClient(kickedClient);
        sendRoomPlayerList(room);
        try {
            kickedClient.sendMessage("waitingRoom kick");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

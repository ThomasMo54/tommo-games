package com.motompro.tommogames.server;

import com.motompro.tcplib.server.ServerSideClient;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

public class GameClient extends ServerSideClient {

    private String name;

    public GameClient(UUID uuid, Socket socket) throws IOException {
        super(uuid, socket);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

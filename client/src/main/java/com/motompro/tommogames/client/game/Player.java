package com.motompro.tommogames.client.game;

import java.util.UUID;

public class Player {

    private final UUID uuid;
    private final String name;

    public Player(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }
}

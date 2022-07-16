package com.motompro.tommogames.common;

public class Game {

    private final String id;
    private final String name;
    private final int maxPlayers;
    private final int minPlayers;

    protected Game(String id, String name, int maxPlayers, int minPlayers) {
        this.id = id;
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public static Builder builder() {
        return new Builder();
    }
}

class Builder {

    private String id;
    private String name;
    private int maxPlayers = 0;
    private int minPlayers = 0;

    protected Builder() {}

    public Builder withId(String id) {
        this.id = id;
        return this;
    }

    public Builder withName(String name) {
        this.name = name;
        return this;
    }

    public Builder withMaxPlayers(int maxPlayers) {
        if(maxPlayers <= 0)
            throw new IllegalArgumentException("maxPlayers must be higher than 0");
        this.maxPlayers = maxPlayers;
        return this;
    }

    public Builder withMinPlayers(int minPlayers) {
        if(minPlayers <= 0)
            throw new IllegalArgumentException("minPlayers must be higher than 0");
        this.minPlayers = minPlayers;
        return this;
    }

    public Game build() {
        if(id == null)
            throw new IllegalArgumentException("id must be specified");
        if(name == null)
            throw new IllegalArgumentException("name must be specified");
        if(maxPlayers == 0)
            throw new IllegalArgumentException("maxPlayers must be specified");
        if(minPlayers == 0)
            minPlayers = maxPlayers;
        return new Game(id, name, maxPlayers, minPlayers);
    }
}

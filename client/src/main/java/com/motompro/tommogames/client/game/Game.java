package com.motompro.tommogames.client.game;

import com.motompro.tommogames.common.GameData;
import com.motompro.tommogames.common.GameRules;

import java.util.Set;

public class Game<P extends Player> {

    protected final GameData data;
    protected final GameRules rules;
    protected final Set<P> players;

    public Game(GameData data, GameRules rules, Set<P> players) {
        this.data = data;
        this.rules = rules;
        this.players = players;
    }
}

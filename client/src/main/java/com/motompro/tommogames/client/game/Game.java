package com.motompro.tommogames.client.game;

import com.motompro.tommogames.common.GameData;
import com.motompro.tommogames.common.GameRules;

public class Game {

    protected final GameData data;
    protected final GameRules rules;

    public Game(GameData data, GameRules rules) {
        this.data = data;
        this.rules = rules;
    }
}

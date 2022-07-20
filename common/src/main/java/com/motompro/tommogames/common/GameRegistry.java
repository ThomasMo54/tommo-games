package com.motompro.tommogames.common;

import java.util.HashMap;
import java.util.Map;

public class GameRegistry {

    public static final String CHESS_ID = "chess";

    private static final Map<String, Game> GAMES = new HashMap<>();

    static {
        GAMES.put(CHESS_ID, Game.builder()
                .withId(CHESS_ID)
                .withName("Échecs")
                .withMaxPlayers(2)
                .withDefaultRules(new GameRules()
                        .set("timer", false)
                        .set("timerTime", 60)
                )
                .build()
        );
    }

    public static Map<String, Game> getGames() {
        return GAMES;
    }
}

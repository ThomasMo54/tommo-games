package com.motompro.tommogames.client.game.chess;

import com.motompro.tommogames.client.waitingRoom.WaitingRoom;
import com.motompro.tommogames.client.waitingRoom.rulePanel.IntegerSpinnerRulePanel;
import com.motompro.tommogames.client.waitingRoom.rulePanel.OneChoiceRulePanel;
import com.motompro.tommogames.common.GameData;
import com.motompro.tommogames.common.GameRegistry;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class ChessWaitingRoom extends WaitingRoom {

    public ChessWaitingRoom(boolean owner) {
        super(owner);
        initRulePanels();
    }

    @Override
    public GameData getGameData() {
        return GameRegistry.getGames().get(GameRegistry.CHESS_ID);
    }

    @Override
    public void startGame() {
        Set<ChessPlayer> chessPlayers = players.stream().map(player -> new ChessPlayer(player.getUuid(), player.getName(), true)).collect(Collectors.toSet());
        new ChessGame(getGameData(), rules, chessPlayers);
    }

    private void initRulePanels() {
        rulePanels.put("timer", new OneChoiceRulePanel<>("timer", "Timer", getRules().getBoolean("timer"), owner, new HashMap<String, Boolean>() {{
            put("Avec", true);
            put("Sans", false);
        }}));
        rulePanels.put("timerTime", new IntegerSpinnerRulePanel("timerTime", "Temps du timer (s)", getRules().getInteger("timerTime"), owner, 1, 3600, 1));
    }
}

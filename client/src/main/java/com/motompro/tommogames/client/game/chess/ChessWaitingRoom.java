package com.motompro.tommogames.client.game.chess;

import com.motompro.tommogames.client.waitingRoom.WaitingRoom;
import com.motompro.tommogames.client.waitingRoom.rulePanel.IntegerSpinnerRulePanel;
import com.motompro.tommogames.client.waitingRoom.rulePanel.OneChoiceRulePanel;
import com.motompro.tommogames.common.Game;
import com.motompro.tommogames.common.GameRegistry;

import java.util.HashMap;

public class ChessWaitingRoom extends WaitingRoom {

    public ChessWaitingRoom(boolean owner) {
        super(owner);
        initRulePanels();
    }

    @Override
    public Game getGame() {
        return GameRegistry.getGames().get(GameRegistry.CHESS_ID);
    }

    private void initRulePanels() {
        rulePanels.put("timer", new OneChoiceRulePanel<>("timer", "Timer", getRules().getBoolean("timer"), owner, new HashMap<String, Boolean>() {{
            put("Avec", true);
            put("Sans", false);
        }}));
        rulePanels.put("timerTime", new IntegerSpinnerRulePanel("timerTime", "Temps du timer (s)", getRules().getInteger("timerTime"), owner, 1, 3600, 1));
    }
}

package com.motompro.tommogames.client.game.chess;

import com.motompro.tommogames.client.waitingRoom.WaitingRoom;
import com.motompro.tommogames.client.waitingRoom.rulePanel.ComboBoxPanel;
import com.motompro.tommogames.client.waitingRoom.rulePanel.IntegerSpinnerRulePanel;
import com.motompro.tommogames.client.waitingRoom.rulePanel.OneChoiceRulePanel;
import com.motompro.tommogames.common.GameData;
import com.motompro.tommogames.common.GameRegistry;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class ChessWaitingRoom extends WaitingRoom {

    private ComboBoxPanel<String> whitePlayerRulePanel;

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
        Set<ChessPlayer> chessPlayers = players.stream().map(player -> new ChessPlayer(player.getUuid(), player.getName(), whitePlayerRulePanel.getValue().toString().equals(player.getUuid().toString()))).collect(Collectors.toSet());
        new ChessGame(getGameData(), rules, chessPlayers);
    }

    private void initRulePanels() {
        rulePanels.put("timer", new OneChoiceRulePanel<>("timer", "Timer", getRules().getBoolean("timer"), owner, new HashMap<String, Boolean>() {{
            put("Avec", true);
            put("Sans", false);
        }}));
        rulePanels.put("timerTime", new IntegerSpinnerRulePanel("timerTime", "Temps du timer (s)", getRules().getInteger("timerTime"), owner, 1, 3600, 1));
        this.whitePlayerRulePanel = new ComboBoxPanel<>("whitePlayer", "Joueur blanc", getRules().getString("whitePlayer"), owner, new HashMap<>());
        rulePanels.put("whitePlayer", whitePlayerRulePanel);
    }

    @Override
    protected void playerListUpdated() {
        super.playerListUpdated();
        HashMap<String, String> playersUuidName = new HashMap<>();
        players.forEach(player -> playersUuidName.put(player.getName(), player.getUuid().toString()));
        whitePlayerRulePanel.setChoices(playersUuidName);
    }
}

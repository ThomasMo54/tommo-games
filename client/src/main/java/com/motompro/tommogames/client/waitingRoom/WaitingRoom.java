package com.motompro.tommogames.client.waitingRoom;

import com.motompro.tcplib.client.Client;
import com.motompro.tcplib.client.ServerListener;
import com.motompro.tommogames.client.TomMoGames;
import com.motompro.tommogames.client.game.Player;
import com.motompro.tommogames.client.waitingRoom.rulePanel.RulePanel;
import com.motompro.tommogames.client.window.MainWindow;
import com.motompro.tommogames.client.window.panel.GamesMenuPanel;
import com.motompro.tommogames.common.GameData;
import com.motompro.tommogames.common.GameRules;

import java.util.*;

public abstract class WaitingRoom implements ServerListener {

    protected final boolean owner;
    private String code;
    private WaitingRoomPanel panel;
    protected final Set<Player> players = new HashSet<>();
    protected final GameRules rules;
    protected final Map<String, RulePanel> rulePanels = new HashMap<>();

    public WaitingRoom(boolean owner) {
        this.owner = owner;
        this.rules = getGameData().getDefaultRules();
        TomMoGames.getInstance().getClient().addServerListener(this);
    }

    public boolean isOwner() {
        return owner;
    }

    public String getCode() {
        return code;
    }

    public GameRules getRules() {
        return rules;
    }

    public Map<String, RulePanel> getRulePanels() {
        return rulePanels;
    }

    public abstract GameData getGameData();

    public abstract void startGame();

    protected void playerListUpdated() {
        panel.updatePlayerList(players);
    }

    @Override
    public void onServerMessage(String message) {
        String[] splitMessage = message.split(" ");
        if(!splitMessage[0].equals("waitingRoom") || splitMessage.length <= 1)
            return;
        Client client = TomMoGames.getInstance().getClient();
        MainWindow window = TomMoGames.getInstance().getMainWindow();
        switch(splitMessage[1]) {
            case "error": {
                client.removeServerListener(this);
                window.showError(MainWindow.DEFAULT_ERROR_MESSAGE);
                window.showPanel(new GamesMenuPanel());
                break;
            }
            case "wrongCode": {
                client.removeServerListener(this);
                window.showError(MainWindow.WRONG_CODE_ERROR_MESSAGE);
                window.showPanel(new GamesMenuPanel());
                break;
            }
            case "full": {
                client.removeServerListener(this);
                window.showError(MainWindow.ROOM_FULL_ERROR_MESSAGE);
                window.showPanel(new GamesMenuPanel());
                break;
            }
            case "joinSuccess": {
                if(splitMessage.length < 3)
                    return;
                this.code = splitMessage[2];
                this.panel = new WaitingRoomPanel(this);
                TomMoGames.getInstance().getMainWindow().showPanel(panel);
                break;
            }
            case "playerList": {
                if(splitMessage.length < 3 || panel == null)
                    return;
                players.clear();
                for(int i = 2; i < splitMessage.length; i += 2)
                    players.add(new Player(UUID.fromString(splitMessage[i]), splitMessage[i + 1]));
                playerListUpdated();
                break;
            }
            case "kick": {
                client.removeServerListener(this);
                window.showPanel(new GamesMenuPanel());
                window.showWarning("Exclusion", "Vous avez ??t?? exclu de la salle d'attente");
                break;
            }
            case "rules": {
                if(splitMessage.length < 4)
                    return;
                for(int i = 2; i < splitMessage.length; i += 2)
                    rulePanels.get(splitMessage[i]).setValue(splitMessage[i + 1]);
                break;
            }
            case "start": {
                client.removeServerListener(this);
                startGame();
                break;
            }
        }
    }

    @Override
    public void onServerDisconnect() {}
}

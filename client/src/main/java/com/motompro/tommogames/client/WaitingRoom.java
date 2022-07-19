package com.motompro.tommogames.client;

import com.motompro.tcplib.client.Client;
import com.motompro.tcplib.client.ServerListener;
import com.motompro.tommogames.client.window.MainWindow;
import com.motompro.tommogames.client.window.panel.GamesMenuPanel;
import com.motompro.tommogames.client.window.panel.WaitingRoomPanel;
import com.motompro.tommogames.common.Game;

import java.util.*;

public class WaitingRoom implements ServerListener {

    private final Game game;
    private final boolean owner;
    private WaitingRoomPanel panel;
    private final Map<UUID, String> players = new HashMap<>();

    public WaitingRoom(Game game, boolean owner) {
        this.game = game;
        this.owner = owner;
        TomMoGames.getInstance().getClient().addServerListener(this);
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
            case "joinSuccess": {
                if(splitMessage.length < 3)
                    return;
                this.panel = new WaitingRoomPanel(game, splitMessage[2]);
                TomMoGames.getInstance().getMainWindow().showPanel(panel);
                break;
            }
            case "playerList": {
                if(splitMessage.length < 3 || panel == null)
                    return;
                players.clear();
                for(int i = 2; i < splitMessage.length; i += 2)
                    players.put(UUID.fromString(splitMessage[i]), splitMessage[i + 1]);
                panel.updatePlayerList(players);
                break;
            }
        }
    }

    @Override
    public void onServerDisconnect() {}
}

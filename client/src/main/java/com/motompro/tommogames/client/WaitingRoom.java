package com.motompro.tommogames.client;

import com.motompro.tcplib.client.Client;
import com.motompro.tcplib.client.ServerListener;
import com.motompro.tommogames.client.window.MainWindow;
import com.motompro.tommogames.client.window.panel.GamesMenuPanel;
import com.motompro.tommogames.client.window.panel.WaitingRoomPanel;
import com.motompro.tommogames.common.Game;

public class WaitingRoom implements ServerListener {

    private final Game game;
    private final boolean owner;
    private WaitingRoomPanel panel;

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
                break;
            }
            case "joinSuccess": {
                if(splitMessage.length < 3)
                    return;
                this.panel = new WaitingRoomPanel(game, splitMessage[2]);
                TomMoGames.getInstance().getMainWindow().showPanel(panel);
                break;
            }
        }
    }

    @Override
    public void onServerDisconnect() {}
}

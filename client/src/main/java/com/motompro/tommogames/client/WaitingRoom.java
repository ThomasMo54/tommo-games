package com.motompro.tommogames.client;

import com.motompro.tcplib.client.ServerListener;
import com.motompro.tommogames.client.window.MainWindow;
import com.motompro.tommogames.client.window.panel.GamesMenuPanel;
import com.motompro.tommogames.client.window.panel.WaitingRoomPanel;
import com.motompro.tommogames.common.Game;

public class WaitingRoom implements ServerListener {

    private final Game game;
    private WaitingRoomPanel panel;

    public WaitingRoom(Game game) {
        this.game = game;
        TomMoGames.getInstance().getClient().addServerListener(this);
    }

    @Override
    public void onServerMessage(String message) {
        String[] splitMessage = message.split(" ");
        if(!splitMessage[0].equals("waitingRoom") || splitMessage.length <= 1)
            return;
        switch(splitMessage[1]) {
            case "error": {
                MainWindow window = TomMoGames.getInstance().getMainWindow();
                window.showError("Une erreur est survenue");
                window.showPanel(new GamesMenuPanel());
                break;
            }
            case "code": {
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

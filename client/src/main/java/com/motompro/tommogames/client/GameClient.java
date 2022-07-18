package com.motompro.tommogames.client;

import com.motompro.tcplib.client.Client;
import com.motompro.tcplib.client.ServerListener;
import com.motompro.tommogames.client.window.MainWindow;
import com.motompro.tommogames.client.window.panel.ConnectionPanel;
import com.motompro.tommogames.client.window.panel.GamesMenuPanel;

import java.io.IOException;

public class GameClient extends Client implements ServerListener {

    public GameClient(String ip, int port) throws IOException {
        super(ip, port);
        this.addServerListener(this);
    }

    @Override
    public void onServerDisconnect() {
        TomMoGames.getInstance().getMainWindow().showError("La connexion avec le serveur vient d'être perdue");
        System.exit(0);
    }

    @Override
    public void onServerMessage(String message) {
        String[] splitMessage = message.split(" ");
        switch(splitMessage[0]) {
            case "log": {
                MainWindow window = TomMoGames.getInstance().getMainWindow();
                if(splitMessage.length == 1 || splitMessage[1].equals("error")) {
                    window.showPanel(new ConnectionPanel());
                    return;
                }
                window.showPanel(new GamesMenuPanel());
                break;
            }
        }
    }
}

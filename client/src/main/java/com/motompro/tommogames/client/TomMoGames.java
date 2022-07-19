package com.motompro.tommogames.client;

import com.motompro.tommogames.client.window.MainWindow;
import com.motompro.tommogames.client.window.panel.ConnectionPanel;

import java.io.IOException;

public class TomMoGames {

    private static TomMoGames instance;
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 54150;

    private final MainWindow mainWindow;
    private final GameClient client;
    private String username;

    protected TomMoGames() {
        this.mainWindow = new MainWindow();
        mainWindow.showPanel(new ConnectionPanel());
        try {
            this.client = new GameClient(SERVER_IP, SERVER_PORT);
        } catch (IOException e) {
            mainWindow.showError(MainWindow.COMMUNICATION_ERROR_MESSAGE);
            System.exit(0);
            throw new RuntimeException(e);
        }
    }

    public static TomMoGames getInstance() {
        return instance;
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }

    public GameClient getClient() {
        return client;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static void main(String[] args) {
        instance = new TomMoGames();
    }
}

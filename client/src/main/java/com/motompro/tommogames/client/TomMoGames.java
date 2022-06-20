package com.motompro.tommogames.client;

import com.motompro.tommogames.client.window.MainWindow;

import java.io.IOException;

public class TomMoGames {

    private static TomMoGames instance;
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 54150;

    private final GameClient client;
    private final MainWindow mainWindow;

    protected TomMoGames() {
        try {
            this.client = new GameClient(SERVER_IP, SERVER_PORT);
            this.mainWindow = new MainWindow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static TomMoGames getInstance() {
        return instance;
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }

    public static void main(String[] args) {
        instance = new TomMoGames();
    }
}

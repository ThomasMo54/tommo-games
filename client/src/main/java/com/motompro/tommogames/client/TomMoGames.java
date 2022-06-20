package com.motompro.tommogames.client;

import com.motompro.tommogames.client.window.MainWindow;

public class TomMoGames {

    private static TomMoGames instance;

    private final MainWindow mainWindow;

    protected TomMoGames() {
        this.mainWindow = new MainWindow();
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

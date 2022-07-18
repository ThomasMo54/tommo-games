package com.motompro.tommogames.client.window.panel;

import com.motompro.tommogames.common.Game;

import javax.swing.*;

public class WaitingRoomPanel extends JPanel {

    private final Game game;
    private final String code;

    public WaitingRoomPanel(Game game, String code) {
        this.game = game;
        this.code = code;
        init();
    }

    private void init() {

    }
}

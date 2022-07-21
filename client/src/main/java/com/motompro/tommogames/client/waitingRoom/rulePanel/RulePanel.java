package com.motompro.tommogames.client.waitingRoom.rulePanel;

import javax.swing.*;
import java.awt.*;

public abstract class RulePanel extends JPanel {

    private static final Font TITLE_FONT = new Font("verdana", Font.BOLD, 14);

    protected final String title;
    protected final Object defaultValue;

    public RulePanel(String title, Object defaultValue) {
        this.title = title;
        this.defaultValue = defaultValue;
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(TITLE_FONT);
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        this.add(titleLabel, constraints);
    }

    public abstract Object getValue();

    public abstract void setValue(Object value);
}

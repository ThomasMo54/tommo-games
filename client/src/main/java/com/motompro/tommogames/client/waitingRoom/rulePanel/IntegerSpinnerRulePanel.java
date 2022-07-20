package com.motompro.tommogames.client.waitingRoom.rulePanel;

import javax.swing.*;
import java.awt.*;

public class IntegerSpinnerRulePanel extends RulePanel {

    private final JSpinner spinner;

    public IntegerSpinnerRulePanel(String rule, String title, int defaultValue, int min, int max, int stepSize) {
        super(rule, title, defaultValue);
        GridBagConstraints constraints = new GridBagConstraints();
        SpinnerModel model = new SpinnerNumberModel(defaultValue, min, max, stepSize);
        this.spinner = new JSpinner(model);
        constraints.gridy = 1;
        this.add(spinner, constraints);
    }

    @Override
    public Object getValue() {
        return spinner.getValue();
    }
}

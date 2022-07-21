package com.motompro.tommogames.client.waitingRoom.rulePanel;

import javax.swing.*;
import java.awt.*;

public class IntegerSpinnerRulePanel extends RulePanel {

    private final JSpinner spinner;

    public IntegerSpinnerRulePanel(String title, int defaultValue, int min, int max, int stepSize) {
        super(title, defaultValue);
        GridBagConstraints constraints = new GridBagConstraints();
        SpinnerModel model = new SpinnerNumberModel(defaultValue, min, max, stepSize);
        this.spinner = new JSpinner(model);
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.insets = new Insets(5, 0, 0, 0);
        constraints.gridy = 1;
        this.add(spinner, constraints);
    }

    @Override
    public Object getValue() {
        return spinner.getValue();
    }

    @Override
    public void setValue(Object value) {
        spinner.setValue(value);
    }
}

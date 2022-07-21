package com.motompro.tommogames.client.waitingRoom.rulePanel;

import com.motompro.tommogames.client.TomMoGames;

import javax.swing.*;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.io.IOException;

public class IntegerSpinnerRulePanel extends RulePanel {

    private final JSpinner spinner;

    public IntegerSpinnerRulePanel(String rule, String title, int defaultValue, boolean canEdit, int min, int max, int stepSize) {
        super(rule, title, defaultValue, canEdit);
        GridBagConstraints constraints = new GridBagConstraints();
        SpinnerModel model = new SpinnerNumberModel(defaultValue, min, max, stepSize);
        this.spinner = new JSpinner(model);
        spinner.setEnabled(canEdit);
        ((DefaultFormatter) ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().getFormatter()).setCommitsOnValidEdit(true);
        spinner.addChangeListener(e -> {
            try {
                TomMoGames.getInstance().getClient().sendMessage("waitingRoom rules " + rule + " " + spinner.getValue());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
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
        spinner.setValue(Integer.parseInt(value.toString()));
    }
}

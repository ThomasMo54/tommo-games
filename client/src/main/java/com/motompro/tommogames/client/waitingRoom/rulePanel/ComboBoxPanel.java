package com.motompro.tommogames.client.waitingRoom.rulePanel;

import com.motompro.tommogames.client.TomMoGames;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class ComboBoxPanel<T> extends RulePanel {

    private Map<String, T> choices;
    private final JComboBox<String> comboBox;

    public ComboBoxPanel(String rule, String title, String defaultValue, boolean canEdit, Map<String, T> choices) {
        super(rule, title, defaultValue, canEdit);
        this.choices = choices;
        GridBagConstraints constraints = new GridBagConstraints();
        this.comboBox = new JComboBox<>(choices.keySet().toArray(new String[0]));
        setValue(defaultValue);
        comboBox.setEnabled(canEdit);
        comboBox.addItemListener(e -> {
            try {
                TomMoGames.getInstance().getClient().sendMessage("waitingRoom rules " + rule + " " + getValue());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.insets = new Insets(5, 0, 0, 0);
        constraints.gridy = 1;
        this.add(comboBox, constraints);
    }

    public void setChoices(Map<String, T> choices) {
        this.choices = choices;
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(choices.keySet().toArray(new String[0]));
        comboBox.setModel(model);
        comboBox.setSelectedIndex(0);
    }

    @Override
    public Object getValue() {
        if(comboBox.getSelectedItem() == null)
            return defaultValue;
        return choices.get(comboBox.getSelectedItem().toString());
    }

    @Override
    public void setValue(Object value) {
        Optional<Map.Entry<String, T>> defaultChoice = choices.entrySet().stream().filter(entry -> entry.getValue().equals(value)).findFirst();
        if(defaultChoice.isPresent())
            comboBox.setSelectedItem(defaultChoice.get().getKey());
        else
            comboBox.setSelectedItem(null);
    }
}

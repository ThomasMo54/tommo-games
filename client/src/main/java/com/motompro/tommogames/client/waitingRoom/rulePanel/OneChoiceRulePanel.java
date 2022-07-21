package com.motompro.tommogames.client.waitingRoom.rulePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class OneChoiceRulePanel<T> extends RulePanel {

    private final Map<String, T> choices;
    private final ButtonGroup buttonGroup = new ButtonGroup();

    public OneChoiceRulePanel(String title, Map<String, T> choices, T defaultValue) {
        super(title, defaultValue);
        this.choices = choices;
        GridBagConstraints constraints = new GridBagConstraints();
        AtomicInteger atomicGridY = new AtomicInteger(1);
        choices.forEach((label, value) -> {
            JRadioButton radio = new JRadioButton(label);
            if(value.equals(defaultValue))
                radio.setSelected(true);
            buttonGroup.add(radio);
            constraints.gridy = atomicGridY.getAndIncrement();
            this.add(radio, constraints);
        });
    }

    @Override
    public Object getValue() {
        Enumeration<AbstractButton> buttons = buttonGroup.getElements();
        while(buttons.hasMoreElements()) {
            JRadioButton radio = (JRadioButton) buttons.nextElement();
            if(!radio.isSelected())
                continue;
            return choices.get(radio.getText());
        }
        return defaultValue;
    }

    @Override
    public void setValue(Object value) {
        Enumeration<AbstractButton> buttons = buttonGroup.getElements();
        while(buttons.hasMoreElements()) {
            JRadioButton radio = (JRadioButton) buttons.nextElement();
            radio.setSelected(value.equals(choices.get(radio.getText())));
        }
    }
}

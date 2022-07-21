package com.motompro.tommogames.common;

import java.util.HashMap;
import java.util.Map;

public class GameRules {

    private final Map<String, Object> rules = new HashMap<>();

    public GameRules set(String rule, Object value) {
        rules.put(rule, value);
        return this;
    }

    public Object get(String rule) {
        return rules.get(rule);
    }

    public String getString(String rule) {
        return (String) rules.get(rule);
    }

    public int getInteger(String rule) {
        return (int) rules.get(rule);
    }

    public double getDouble(String rule) {
        return (double) rules.get(rule);
    }

    public boolean getBoolean(String rule) {
        return (boolean) rules.get(rule);
    }

    public Map<String, Object> getAll() {
        return rules;
    }
}

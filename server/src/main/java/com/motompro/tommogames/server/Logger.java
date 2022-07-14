package com.motompro.tommogames.server;

import java.time.ZonedDateTime;

public class Logger {

    public static void log(String message) {
        log(message, LogType.INFO);
    }

    public static void log(String message, LogType type) {
        ZonedDateTime time = ZonedDateTime.now();
        String timePrefix = "[" + String.format("%02d", time.getHour()) + ":" + String.format("%02d", time.getMinute()) + ":" + String.format("%02d", time.getSecond()) + "]";
        String typePrefix = "[" + type.name() + "]";
        System.out.println(timePrefix + " " + typePrefix + " " + message);
    }

    public enum LogType {
        INFO, WARNING, ERROR
    }
}
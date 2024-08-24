package com.jurai.util;

public class StateLogger {
    public static void log(String msg) {
        System.out.println("\u001B[35m[STATE LOGGER] \u001B[32mINFO:  \u001B[37m" + msg);
    }

    public static void logError(String msg) {
        System.out.println("\u001B[35m[STATE LOGGER] \u001B[31mERROR:  \u001B[37m" + msg);
    }
}

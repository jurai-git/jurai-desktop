package io.jurai.util;

public class UILogger {
    public static void log(String msg) {
        System.out.println("\u001B[36m[UI LOGGER] \u001B[32mINFO: \u001B[37m" + msg);
    }

    public static void logError(String msg) {
        System.out.println("\u001B[36m[UI LOGGER] \u001B[31mERROR:  \u001B[37m" + msg);
    }

    public static void logWarning(String msg) {
        System.out.println("\u001B[36m[UI LOGGER] \u001B[33mWARNING:  \u001B[37m" + msg);
    }
}

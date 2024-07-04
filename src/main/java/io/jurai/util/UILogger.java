package io.jurai.util;

public class UILogger {
    public static void log(String msg) {
        System.out.println("[UILogger INFO]: " + msg);
    }

    public static void logError(String msg) {
        System.out.println("[UILogger ERROR]: " + msg);
    }

    public static void logWarning(String msg) {
        System.out.println("[UILogger WARNING]: " + msg);
    }
}

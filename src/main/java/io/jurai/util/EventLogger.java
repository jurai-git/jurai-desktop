package io.jurai.util;

public class EventLogger {
    public static void log(String msg) {
        System.out.println("\u001B[34m[EVENT LOGGER] \u001B[32mINFO: \u001B[37m " + msg);
    }
}

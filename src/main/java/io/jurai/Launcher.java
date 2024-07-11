package io.jurai;

public class Launcher {
    public static void main(String[] args) {
        CommandListener commandListener = new CommandListener();
        Thread commandThread = new Thread(commandListener);
        commandThread.start();
        App.main(args);
    }
}

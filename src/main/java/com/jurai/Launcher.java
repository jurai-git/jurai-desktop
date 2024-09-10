package com.jurai;

import com.jurai.data.ApplicationState;

public class Launcher {
    public static void main(String[] args) {
        CommandListener commandListener = new CommandListener();
        Thread commandThread = new Thread(commandListener);
        commandThread.start();
        ApplicationState.setDebugging(true);

        App.setCtlThread(commandThread);
        App.main(args);
    }
}

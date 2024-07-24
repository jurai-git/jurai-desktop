package io.jurai;

import io.jurai.data.ApplicationState;

public class Launcher {
    public static void main(String[] args) {
        CommandListener commandListener = new CommandListener();
        Thread commandThread = new Thread(commandListener);

        ApplicationState.initialize();

        ApplicationState.addPropertyChangeListener(e -> {
            if(e.getPropertyName() == "debugging") {
                if((Boolean) e.getNewValue()) {
                    commandThread.start();
                } else {
                    commandThread.interrupt();
                }
            }
        });

        for(String arg : args) {
            if(arg.equals("--debug")) ApplicationState.setDebugging(true);
        }

        App.setCtlThread(commandThread);
        App.main(args);
    }
}

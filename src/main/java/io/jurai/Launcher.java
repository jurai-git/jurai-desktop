package io.jurai;

import io.jurai.data.ApplicationState;

public class Launcher {
    public static void main(String[] args) {
        CommandListener commandListener = new CommandListener();
        Thread commandThread = new Thread(commandListener);


        for(String arg : args) {
            if(arg.equals("--debug")) {
                ApplicationState.setDebugging(true);

            }
        }

        App.setCtlThread(commandThread);
        App.main(args);
    }
}

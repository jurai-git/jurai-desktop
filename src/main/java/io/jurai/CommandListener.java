package io.jurai;

import io.jurai.data.ApplicationState;
import javafx.application.Platform;

import java.util.Arrays;
import java.util.Scanner;

public class CommandListener implements Runnable {
    private boolean running;

    public CommandListener() {
        running = true;
    }

    @Override
    public void run() {
        while(running) {
            String input = new Scanner(System.in).nextLine().toLowerCase();
            handleCommand(input);
        }
    }

    private void handleCommand(String input) {
        String[] commands = input.split(" ");

        String[] args = Arrays.copyOfRange(commands, 1, commands.length);
        String command = commands[0];

        switch(command) {
            case "fxctl":
                handleFxctl(args);
                break;
            default:
                System.out.println("Unknown command: " + command);
        }
    }

    private void handleFxctl(String[] args) {
        switch(args[0]) {
            case "export":
            case "--export":
            case "-e":
                String key = args[1];
                String newValue = args[2];
                try {
                    switch (key) {
                        case "loggedin":
                            Platform.runLater(() -> ApplicationState.setLoggedIn(Boolean.parseBoolean(newValue)));
                            System.out.println(key + " property changed");
                            break;
                        default:
                            System.out.println("Invalid key: " + key);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Invalid value: " + newValue);
                }
                break;
            default:
                System.out.println("Invalid fxctl command: " + args[0]);
                System.out.println("Avaliable commands: --export | -e: exports a value to the ApplicationState");
        }
    }

    public void stop() {
        running = false;
    }
}

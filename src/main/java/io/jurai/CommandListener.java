package io.jurai;

import io.jurai.data.ApplicationState;
import io.jurai.data.model.*;
import javafx.application.Platform;

import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class CommandListener implements Runnable {
    private boolean running;
    private Advogado dummyUser;

    public CommandListener() {
        running = true;
    }

    @Override
    public void run() {
        dummyUser = new Advogado("AdvogadoPadrao", "EmailPadrao@jurai.com", "SenhaPadrao");
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
                        case "currentuser":
                            if(newValue.equals("default")) {
                                Platform.runLater(() -> ApplicationState.setCurrentUser(dummyUser));
                            } else {
                                System.out.println("Custom user setting is not yet supported");
                            }
                            break;
                        default:
                            System.out.println("Invalid key: " + key);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Invalid value: " + newValue);
                }
                break;
            case "stop-debug":
            case "--stop-debug":
            case "-s":
                System.out.println("Are you sure? You will not be able to turn debugging again. [N/y]");
                if(new Scanner(System.in).nextLine().toLowerCase().equals("y"))
                    ApplicationState.setDebugging(false);
                else
                    System.out.println("Aborting...");
                break;
            default:
                System.out.println("Invalid fxctl command: " + args[0]);
                System.out.println("Avaliable commands:\n" +
                        " --export | -e | export: exports a value to the ApplicationState\n" +
                        " --stop-debug | -s | stop-debug: stops debugging (once you stop it, you cant turn it back!)");
        }
    }

    public void stop() {
        running = false;
    }
}

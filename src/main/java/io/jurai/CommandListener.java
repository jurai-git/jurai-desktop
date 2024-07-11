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
        dummyUser = new Advogado("123123123-12" , TipoPessoa.PESSOA_FISICA, "NomeUsuario", Genero.FEMININO, new Date(), Nacionalidade.BRASILEIRO, "eu-nao-sei-como-é-uma-oab", "email@email", new Endereco("12123123", "rua", "bairro", "cidade", "estado", "123"), null);
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
            default:
                System.out.println("Invalid fxctl command: " + args[0]);
                System.out.println("Avaliable commands: --export | -e: exports a value to the ApplicationState");
        }
    }

    public void stop() {
        running = false;
    }
}

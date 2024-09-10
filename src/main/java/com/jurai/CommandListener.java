package com.jurai;

import com.jurai.data.ApplicationState;
import com.jurai.data.model.*;
import com.jurai.data.serializer.AdvogadoSerializer;
import com.jurai.data.serializer.RequerenteSerializer;
import com.jurai.data.service.AdvogadoService;
import javafx.application.Platform;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class CommandListener implements Runnable {
    private boolean running;
    private Advogado dummyUser;

    public CommandListener() {
        running = true;
    }

    @Override
    public void run() {
        dummyUser = new Advogado(1, "nomePadrao", "emailPadrao", "oabPadrao", "tokenPadrao");
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
            case "import":
                if(args[1].equals("requerente")) {
                    Requerente r = new Requerente(Integer.toString(new Random().nextInt()), "nome",
                            "nomeSocial", "F", false, "12312312", "ssp",
                            "solteira", "brasileira", "profissao", "123123123",
                            "logradouro", Integer.toString(new Random().nextInt()), "123", "comp",
                            "bairro", "estado", "cidade");
                    System.out.println(r);
                    try {
                        new AdvogadoService().addRequerente(r);
                    } catch(Exception e) {
                        e.printStackTrace();
                        System.out.println("bééééééé");
                    }
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
                System.out.println("""
                        Avaliable commands:
                         --export | -e | export: exports a value to the ApplicationState
                         --stop-debug | -s | stop-debug: stops debugging (once you stop it, you cant turn it back!)""");
        }
    }

    public void stop() {
        running = false;
    }
}

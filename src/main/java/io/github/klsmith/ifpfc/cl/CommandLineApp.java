package io.github.klsmith.ifpfc.cl;

import java.util.Arrays;
import java.util.Scanner;

import io.github.klsmith.ifpfc.cl.command.Command;
import io.github.klsmith.ifpfc.cl.command.ConvertCommand;
import io.github.klsmith.ifpfc.cl.command.ErrorCommand;
import io.github.klsmith.ifpfc.cl.command.HelpCommand;
import io.github.klsmith.ifpfc.cl.command.StopCommand;

public class CommandLineApp {

    private boolean running = false;

    public static void main(String[] args) {
        final CommandLineApp app = new CommandLineApp();
        app.run();
    }

    public void run() {
        try (final Scanner scanner = new Scanner(System.in)) {
            run(scanner);
        }
    }

    private void run(Scanner scanner) {
        running = true;
        printGreeting();
        while (running) {
            readCommand(scanner)
                    .execute();
        }
    }

    private void printGreeting() {
        System.out.println("Welcome to The Infix/Postfix Converter!");
        System.out.println("Type 'help' for list of commands.");
    }

    private Command readCommand(Scanner scanner) {
        final String input = readInput(scanner);
        final String[] args = input.split(" ");
        if (args.length > 0) {
            final String command = args[0];
            final String[] rest = args.length > 1 ? Arrays.copyOfRange(args, 1, args.length) : null;
            switch (command) {
                case "exit":
                    return new StopCommand(this);
                case "help":
                    return new HelpCommand(System.out);
                case "convert":
                    return new ConvertCommand(System.out, rest);
            }
        }
        return new ErrorCommand(System.out, args);
    }

    private String readInput(Scanner scanner) {
        System.out.print("> ");
        return scanner.nextLine();
    }

    public void stop() {
        running = false;
    }

}

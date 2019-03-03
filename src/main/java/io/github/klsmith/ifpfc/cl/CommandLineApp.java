package io.github.klsmith.ifpfc.cl;

import java.util.Arrays;
import java.util.Scanner;

import io.github.klsmith.ifpfc.cl.command.Command;
import io.github.klsmith.ifpfc.cl.command.ConvertCommand;
import io.github.klsmith.ifpfc.cl.command.ErrorCommand;
import io.github.klsmith.ifpfc.cl.command.HelpCommand;
import io.github.klsmith.ifpfc.cl.command.ResolveCommand;
import io.github.klsmith.ifpfc.cl.command.ExitCommand;

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
            final String input = readInput(scanner);
            final Command command = readCommand(input);
            command.execute();
        }
    }

    private void printGreeting() {
        System.out.println("Welcome to The Infix/Postfix Converter!");
        System.out.println("Type 'help' for list of commands.");
    }

    protected Command readCommand(String input) {
        final String[] args = input.split(" ");
        if (args.length > 0) {
            final String command = args[0];
            final String[] rest = args.length > 1 ? Arrays.copyOfRange(args, 1, args.length) : null;
            switch (command) {
                case "exit":
                    return new ExitCommand(this);
                case "help":
                    return new HelpCommand(System.out);
                case "convert":
                    return new ConvertCommand(System.out, rest);
                case "resolve":
                    return new ResolveCommand(System.out, rest);
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

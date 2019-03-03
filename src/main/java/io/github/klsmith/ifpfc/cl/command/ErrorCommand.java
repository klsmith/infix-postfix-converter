package io.github.klsmith.ifpfc.cl.command;

import java.io.PrintStream;

public class ErrorCommand implements Command {

    private final PrintStream out;
    private final String command;

    public ErrorCommand(PrintStream out, String... args) {
        this(out, String.join(" ", args));
    }

    public ErrorCommand(PrintStream out, String command) {
        this.out = out;
        this.command = command;
    }

    @Override
    public void execute() {
        out.printf("Command '%s' not recognized, please try again.\n\n", command);
    }

    @Override
    public String toString() {
        return String.format("Error(%s)", command);
    }

}

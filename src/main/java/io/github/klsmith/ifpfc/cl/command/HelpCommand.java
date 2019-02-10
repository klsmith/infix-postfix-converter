package io.github.klsmith.ifpfc.cl.command;

import java.io.PrintStream;

public class HelpCommand implements Command {

    private final PrintStream out;

    public HelpCommand(PrintStream out) {
        this.out = out;
    }

    @Override
    public void execute() {
        out.println("Commands:");
        out.println("'exit' : Exits the program.");
        out.println("'help' : Displays this list.");
        out.println("'convert <fromType> <toType> <input>' : ");
        out.println("\tConverts the given input from the <fromType> into the <toType>");
        out.println();
    }

}

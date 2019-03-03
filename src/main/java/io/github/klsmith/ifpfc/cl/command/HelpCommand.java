package io.github.klsmith.ifpfc.cl.command;

import java.io.PrintStream;
import java.util.Objects;

public final class HelpCommand implements Command {

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
        out.println("'resolve <type> <input>' : ");
        out.println("\tResolves the given input, expected to be of the given type.");
        out.println();
    }

    @Override
    public int hashCode() {
        return Objects.hash(out);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HelpCommand) {
            final HelpCommand other = (HelpCommand) obj;
            return Objects.equals(out, other.out);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Help()";
    }

}

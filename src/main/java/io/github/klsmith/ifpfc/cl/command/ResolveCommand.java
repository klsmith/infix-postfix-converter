package io.github.klsmith.ifpfc.cl.command;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

import io.github.klsmith.ifpfc.ArithmeticParser;
import io.github.klsmith.ifpfc.arithmetic.Arithmetic;
import io.github.klsmith.ifpfc.cl.command.ConvertCommand.Type;

public class ResolveCommand implements Command {

    private final PrintStream out;
    private final Type type;
    private final String input;

    public ResolveCommand(PrintStream out, Type type, String input) {
        this.out = out;
        this.type = type;
        this.input = input;
    }

    public ResolveCommand(PrintStream out, String... args) {
        this(out,
                Type.fromString(args[0]),
                String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
    }

    @Override
    public void execute() {
        final ArithmeticParser parser = type.getParser();
        final Arithmetic arithmetic = parser.parse(input);
        final BigDecimal resolution = arithmetic.resolve();
        out.printf("\t%s = %s\n\n", input, resolution);
    }

    @Override
    public int hashCode() {
        return Objects.hash(out, type, input);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ResolveCommand) {
            final ResolveCommand other = (ResolveCommand) obj;
            return Objects.equals(out, other.out)
                    && Objects.equals(type, other.type)
                    && Objects.equals(input, other.input);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Resolve(%s)",
                String.join(", ", type.toString(), input));
    }

}

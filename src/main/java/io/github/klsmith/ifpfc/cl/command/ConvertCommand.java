package io.github.klsmith.ifpfc.cl.command;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Objects;

import io.github.klsmith.ifpfc.ArithmeticParser;
import io.github.klsmith.ifpfc.ArithmeticStringWriter;
import io.github.klsmith.ifpfc.InfixArithmeticParser;
import io.github.klsmith.ifpfc.InfixArithmeticStringWriter;
import io.github.klsmith.ifpfc.PostfixArithmeticParser;
import io.github.klsmith.ifpfc.PostfixArithmeticStringWriter;
import io.github.klsmith.ifpfc.arithmetic.Arithmetic;

public final class ConvertCommand implements Command {

    private final PrintStream out;
    private final Type from;
    private final Type to;
    private final String input;

    public ConvertCommand(PrintStream out, String... args) {
        this(out, Type.fromString(args[0]),
                Type.fromString(args[1]),
                String.join(" ", Arrays.copyOfRange(args, 2, args.length)));
    }

    public ConvertCommand(PrintStream out, Type from, Type to, String input) {
        this.out = out;
        this.from = from;
        this.to = to;
        this.input = input;
    }

    @Override
    public void execute() {
        final String output = convert();
        final int displayWidth = getDisplayWidth();
        out.printf("%" + displayWidth + "s: %s\n", from, input);
        out.printf("%" + displayWidth + "s: %s\n\n", to, output);
    }

    private int getDisplayWidth() {
        return Math.max(
                from.toString().length(),
                to.toString().length());
    }

    private String convert() {
        final ArithmeticParser parser = from.getParser();
        final Arithmetic arithmetic = parser.parse(input);
        final ArithmeticStringWriter writer = to.getWriter();
        return writer.write(arithmetic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(out, from, to, input);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ConvertCommand) {
            final ConvertCommand other = (ConvertCommand) obj;
            return Objects.equals(out, other.out)
                    && Objects.equals(from, other.from)
                    && Objects.equals(to, other.to)
                    && Objects.equals(input, other.input);
        }
        return false;
    }

    public static enum Type {

        INFIX(new InfixArithmeticParser(),
                new InfixArithmeticStringWriter()), //
        POSTFIX(new PostfixArithmeticParser(),
                new PostfixArithmeticStringWriter());

        private final ArithmeticParser parser;
        private final ArithmeticStringWriter writer;

        private Type(ArithmeticParser parser, ArithmeticStringWriter writer) {
            this.parser = parser;
            this.writer = writer;
        }

        public static Type fromString(String string) {
            for (Type type : values()) {
                if (type.toString().equalsIgnoreCase(string)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Type '" + string + "' not found.");
        }

        public ArithmeticParser getParser() {
            return parser;
        }

        public ArithmeticStringWriter getWriter() {
            return writer;
        }

    }

}

package io.github.klsmith.ifpfc.cl;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class Command {

    private final Type type;
    private final Notation notation;
    private final String input;

    private Command(Type type, Notation notation, String input) {
        this.type = type;
        this.notation = notation;
        this.input = input;
    }

    public static Command fromString(String string) {
        Type type = null;
        Notation notation = null;
        String input = null;
        try (final Scanner scanner = new Scanner(string)) {
            scanner.useDelimiter(" ");
            while (scanner.hasNext()) {
                final String token = scanner.next();
                if (null == type) {
                    type = Type.fromString(token)
                            .orElse(null);
                    continue;
                }
                if (null == notation) {
                    notation = Notation.fromString(token)
                            .orElse(null);
                    continue;
                }
                if (null == input) {
                    final List<String> inputList = new LinkedList<>();
                    inputList.add(token);
                    while (scanner.hasNext()) {
                        final String subToken = scanner.next();
                        inputList.add(subToken);
                    }
                    final String[] array = inputList.toArray(new String[inputList.size()]);
                    input = String.join(" ", array);
                }
            }
        }
        return new Command(type, notation, input);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, notation, input);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Command) {
            final Command other = (Command) obj;
            return Objects.equals(type, other.type)
                    && Objects.equals(notation, other.notation)
                    && Objects.equals(input, other.input);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Command(" + String.join(", ",
                type.toString(),
                notation.toString(),
                "\"" + input + "\"") + ")";
    }

    public static enum Type {
        PARSE;

        public static Optional<Type> fromString(String string) {
            for (Type type : values()) {
                if (type.toString().equalsIgnoreCase(string)) {
                    return Optional.of(type);
                }
            }
            return Optional.empty();
        }
    }

    public enum Notation {
        INFIX;

        public static Optional<Notation> fromString(String string) {
            for (Notation notation : values()) {
                if (notation.toString().equalsIgnoreCase(string)) {
                    return Optional.of(notation);
                }
            }
            return Optional.empty();
        }
    }

    public static class Builder {

        private Type type;
        private Notation notation;
        private String input;

        public Builder setCommandType(Type type) {
            this.type = type;
            return this;
        }

        public Builder setNotation(Notation notation) {
            this.notation = notation;
            return this;
        }

        public Builder setInput(String input) {
            this.input = input;
            return this;
        }

        public Command build() {
            return new Command(type, notation, input);
        }

    }

}

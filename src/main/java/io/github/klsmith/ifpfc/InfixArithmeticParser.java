package io.github.klsmith.ifpfc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import io.github.klsmith.ifpfc.arithmetic.Add;
import io.github.klsmith.ifpfc.arithmetic.Arithmetic;
import io.github.klsmith.ifpfc.arithmetic.Divide;
import io.github.klsmith.ifpfc.arithmetic.Multiply;
import io.github.klsmith.ifpfc.arithmetic.Subtract;
import io.github.klsmith.ifpfc.arithmetic.Value;

public class InfixArithmeticParser implements ArithmeticParser {

    @Override
    public Arithmetic parse(String input) {
        final Group group = tokenize(input);
        return parse(group);
    }

    private Group tokenize(String input) {
        final List<Single> list = new LinkedList<>();
        try (final Scanner scanner = new Scanner(input)) {
            scanner.useDelimiter(" ");
            while (scanner.hasNext()) {
                list.add(new Single(scanner.next()));
            }
        }
        return new Group(list);
    }

    private Arithmetic parse(Group inputGroup) {
        Group ouputGroup = inputGroup;
        ouputGroup = groupParenthesis(ouputGroup);
        ouputGroup = groupOperator(ouputGroup, "*");
        ouputGroup = groupOperator(ouputGroup, "/");
        ouputGroup = groupOperator(ouputGroup, "+");
        ouputGroup = groupOperator(ouputGroup, "-");
        return interpret(ouputGroup);
    }

    private Group groupParenthesis(Group group) {
        final LinkedList<Token> list = new LinkedList<>();
        int startIndex = -1;
        int endIndex = -1;
        int index = 0;
        int parenthesisLevel = 0;
        for (Token token : group) {
            if (token.singleEquals("(")) {
                if (parenthesisLevel == 0) {
                    startIndex = index;
                }
                parenthesisLevel++;
            } else if (token.singleEquals(")")) {
                parenthesisLevel--;
                if (parenthesisLevel == 0) {
                    endIndex = index;
                    final Group newGroup = groupParenthesis(group
                            .subGroupExclusive(startIndex, endIndex));
                    list.addLast(newGroup);
                }
            } else if (parenthesisLevel == 0) {
                list.addLast(token);
            }
            index++;
        }
        return trimListToGroup(list);
    }

    private Group trimListToGroup(List<Token> list) {
        if (list.size() == 1) {
            final Optional<Group> optGroup = list.get(0).asGroup();
            if (optGroup.isPresent()) {
                return optGroup.get();
            }
        }
        return new Group(list);
    }

    private Group groupOperator(Group inputGroup, String operator) {
        final LinkedList<Token> list = new LinkedList<>();
        for (Token token : inputGroup) {
            if (list.size() > 1 && list.getLast().singleEquals(operator)) {
                final Token b = list.removeLast();
                final Token a = list.removeLast();
                final Token c = token;
                list.addLast(new Group(a, b, c));
            } else {
                list.addLast(token);
            }
        }
        return trimListToGroup(list);
    }

    private Arithmetic interpret(Token token) {
        final Optional<Group> optGroup = token.asGroup();
        if (optGroup.isPresent()) {
            final Group group = optGroup.get();
            if (group.size() == 3) {
                final Arithmetic a = interpret(group.get(0));
                final Arithmetic b = interpret(group.get(2));
                return getOperatorConstructor(group.get(1)).apply(a, b);
            } else if (group.size() == 1) {
                return interpret(group.get(0));
            }
        }
        final Optional<Single> optSingle = token.asSingle();
        if (optSingle.isPresent()) {
            final Single single = optSingle.get();
            return new Value(readNumber(single));
        }
        throw new IllegalStateException("Cannot interpret token: \"" + token + "\"");
    }

    private BiFunction<Arithmetic, Arithmetic, Arithmetic> getOperatorConstructor(Token token) {
        switch (token.asSingle()
                .map(Single::getValue)
                .orElse("")) {
            case "*":
                return Multiply::new;
            case "/":
                return Divide::new;
            case "+":
                return Add::new;
            case "-":
                return Subtract::new;
        }
        throw new IllegalStateException();
    }

    private BigDecimal readNumber(Single single) {
        try {
            return new BigDecimal(single.getValue());
        }
        catch (NumberFormatException e) {
            throw new IllegalStateException(e);
        }
    }

    private static interface Token {

        boolean singleEquals(String value);

        Optional<Single> asSingle();

        Optional<Group> asGroup();

    }

    private static class Single implements Token {

        private final String value;

        public Single(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public boolean singleEquals(String value) {
            return Objects.equals(this.value, value);
        }

        @Override
        public Optional<Single> asSingle() {
            return Optional.of(this);
        }

        @Override
        public Optional<Group> asGroup() {
            return Optional.empty();
        }

        @Override
        public String toString() {
            return value;
        }

    }

    private static class Group implements Token, Iterable<Token> {

        private final List<Token> list;

        public Group(List<? extends Token> list) {
            this.list = new ArrayList<>(list);
        }

        public Group(Token... tokens) {
            this.list = Arrays.asList(tokens);
        }

        @Override
        public Iterator<Token> iterator() {
            return list.iterator();
        }

        public Token get(int index) {
            return list.get(index);
        }

        public int size() {
            return list.size();
        }

        public Group subGroupExclusive(int start, int end) {
            return new Group(list.subList(start + 1, end));
        }

        @Override
        public boolean singleEquals(String value) {
            return false;
        }

        @Override
        public Optional<Single> asSingle() {
            return Optional.empty();
        }

        @Override
        public Optional<Group> asGroup() {
            return Optional.of(this);
        }

        @Override
        public String toString() {
            final String listString = list.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            return String.format("G(%s)", listString);
        }

    }

}
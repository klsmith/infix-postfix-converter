package io.github.klsmith.ifpfc;

import java.math.BigDecimal;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

import io.github.klsmith.ifpfc.arithmetic.Add;
import io.github.klsmith.ifpfc.arithmetic.Arithmetic;
import io.github.klsmith.ifpfc.arithmetic.Divide;
import io.github.klsmith.ifpfc.arithmetic.Multiply;
import io.github.klsmith.ifpfc.arithmetic.Subtract;
import io.github.klsmith.ifpfc.arithmetic.Value;

public class InfixArithmeticParser implements ArithmeticParser {

    @Override
    public Arithmetic parse(String input) {
        try (final Scanner scanner = new Scanner(input)) {
            scanner.useDelimiter(" ");
            return parse(scanner);
        }
    }

    private Arithmetic parse(Iterator<String> tokens) {
        final Deque<Arithmetic> stack = new LinkedList<>();
        while (tokens.hasNext()) {
            final String token = tokens.next();
            final Optional<Value> value = parseValue(token);
            if (value.isPresent()) {
                stack.push(value.get());
                continue;
            }
            if (Objects.equals(token, "(")) {
                stack.push(parse(getInsideParenthesis(tokens)));
                continue;
            }
            final Arithmetic a = stack.pop();
            Arithmetic b;
            final String next = tokens.next();
            if (Objects.equals(next, "(")) {
                b = parse(getInsideParenthesis(tokens));
            } else {
                b = parse(next);
            }
            switch (token) {
                case "+":
                    stack.push(new Add(a, b));
                    break;
                case "-":
                    stack.push(new Subtract(a, b));
                    break;
                case "*":
                    stack.push(new Multiply(a, b));
                    break;
                case "/":
                    stack.push(new Divide(a, b));
                default:
                    break;
            }
        }
        return stack.pop();
    }

    private Iterator<String> getInsideParenthesis(Iterator<String> tokens) {
        int parenthesisLevel = 1;
        final List<String> subTokens = new LinkedList<>();
        while (tokens.hasNext() && parenthesisLevel > 0) {
            final String subToken = tokens.next();
            if (Objects.equals(subToken, "(")) {
                parenthesisLevel++;
            }
            if (Objects.equals(subToken, ")")) {
                parenthesisLevel--;
            }
            if (parenthesisLevel > 0) {
                subTokens.add(subToken);
            }
        }
        return subTokens.iterator();
    }

    private Optional<Value> parseValue(String token) {
        try {
            return Optional.of(new Value(new BigDecimal(token)));
        }
        catch (NumberFormatException e) {
            // handled with empty return below
        }
        return Optional.empty();
    }

}

package io.github.klsmith.ifpfc;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Scanner;

import io.github.klsmith.ifpfc.arithmetic.Add;
import io.github.klsmith.ifpfc.arithmetic.Arithmetic;

public class PostfixArithmeticParser implements ArithmeticParser {

    @Override
    public Arithmetic parse(String input) {
        final Deque<Integer> stack = new LinkedList<>();
        Arithmetic result = null;
        try (final Scanner scanner = new Scanner(input.trim())) {
            scanner.useDelimiter(" ");
            while (scanner.hasNext()) {
                final String token = scanner.next();
                final Optional<Integer> integer = parseInt(token);
                if (integer.isPresent()) {
                    stack.push(integer.get());
                    continue;
                }
                final Integer b = stack.pop();
                final Integer a = stack.pop();
                result = new Add(a.intValue(), b.intValue());
            }
        }
        return result;
    }

    private Optional<Integer> parseInt(String token) {
        try {
            return Optional.of(Integer.valueOf(token));
        } catch (NumberFormatException e) {
            // handled with empty return below
        }
        return Optional.empty();
    }

}

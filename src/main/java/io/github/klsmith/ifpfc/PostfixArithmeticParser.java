package io.github.klsmith.ifpfc;

import java.math.BigDecimal;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Scanner;

import io.github.klsmith.ifpfc.arithmetic.Add;
import io.github.klsmith.ifpfc.arithmetic.Arithmetic;

public class PostfixArithmeticParser implements ArithmeticParser {

    @Override
    public Arithmetic parse(String input) {
        final Deque<BigDecimal> stack = new LinkedList<>();
        Arithmetic result = null;
        try (final Scanner scanner = new Scanner(input.trim())) {
            scanner.useDelimiter(" ");
            while (scanner.hasNext()) {
                final String token = scanner.next();
                final Optional<BigDecimal> value = parseValue(token);
                if (value.isPresent()) {
                    stack.push(value.get());
                    continue;
                }
                final BigDecimal b = stack.pop();
                final BigDecimal a = stack.pop();
                result = new Add(a, b);
            }
        }
        return result;
    }

    private Optional<BigDecimal> parseValue(String token) {
        final Optional<Integer> integerValue = parseInt(token);
        if (integerValue.isPresent()) {
            return Optional.of(BigDecimal.valueOf(integerValue.get()));
        }
        final Optional<Double> doubleValue = parseDouble(token);
        if (doubleValue.isPresent()) {
            return Optional.of(BigDecimal.valueOf(doubleValue.get()));
        }
        return Optional.empty();
    }

    private Optional<Integer> parseInt(String token) {
        try {
            if (!token.contains(".")) {
                return Optional.of(Integer.valueOf(token));
            }
        } catch (NumberFormatException e) {
            // handled with empty return below
        }
        return Optional.empty();
    }

    private Optional<Double> parseDouble(String token) {
        try {
            return Optional.of(Double.valueOf(token));
        } catch (NumberFormatException e) {
            // handled with empty return below
        }
        return Optional.empty();
    }

}

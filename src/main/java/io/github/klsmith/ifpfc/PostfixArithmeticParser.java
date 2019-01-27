package io.github.klsmith.ifpfc;

import java.math.BigDecimal;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Scanner;

import io.github.klsmith.ifpfc.arithmetic.Add;
import io.github.klsmith.ifpfc.arithmetic.Arithmetic;
import io.github.klsmith.ifpfc.arithmetic.Subtract;

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
                if ("+".equals(token)) {
                    result = new Add(a, b);
                } else {
                    result = new Subtract(a, b);
                }
            }
        }
        return result;
    }

    private Optional<BigDecimal> parseValue(String token) {
        try {
            return Optional.of(new BigDecimal(token));
        } catch (NumberFormatException e) {
            // handled with empty return below
        }
        return Optional.empty();
    }

}

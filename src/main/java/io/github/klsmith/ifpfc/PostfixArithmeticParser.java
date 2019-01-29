package io.github.klsmith.ifpfc;

import java.math.BigDecimal;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Scanner;

import io.github.klsmith.ifpfc.arithmetic.Add;
import io.github.klsmith.ifpfc.arithmetic.Arithmetic;
import io.github.klsmith.ifpfc.arithmetic.Divide;
import io.github.klsmith.ifpfc.arithmetic.Multiply;
import io.github.klsmith.ifpfc.arithmetic.Subtract;
import io.github.klsmith.ifpfc.arithmetic.Value;

public class PostfixArithmeticParser implements ArithmeticParser {

    @Override
    public Arithmetic parse(String input) {
        final Deque<Arithmetic> stack = new LinkedList<>();
        try (final Scanner scanner = new Scanner(input.trim())) {
            scanner.useDelimiter(" ");
            while (scanner.hasNext()) {
                final String token = scanner.next();
                final Optional<Value> value = parseValue(token);
                if (value.isPresent()) {
                    stack.push(value.get());
                    continue;
                }
                final Arithmetic b = stack.pop();
                final Arithmetic a = stack.pop();
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
        }
        return stack.pop();
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

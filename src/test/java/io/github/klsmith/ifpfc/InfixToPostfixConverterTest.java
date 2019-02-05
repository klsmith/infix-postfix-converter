package io.github.klsmith.ifpfc;

import static io.github.klsmith.ifpfc.TestHelper.withSpaces;
import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import io.github.klsmith.ifpfc.arithmetic.Add;
import io.github.klsmith.ifpfc.arithmetic.Arithmetic;

public class InfixToPostfixConverterTest {

    private final InfixToPostfixConverter converter = new InfixToPostfixConverter();
    private final ArithmeticStringWriter infixWriter = new InfixArithmeticStringWriter();
    private final ArithmeticStringWriter postfixWriter = new PostfixArithmeticStringWriter();

    @Test
    public void testSimple() {
        final String input = withSpaces(1, "+", 2, "+", 3, "+", 4);
        final String actual = converter.toPostfix(input);
        final String expected = withSpaces(1, 2, "+", 3, "+", 4, "+");
        assertEquals(expected, actual);
    }

    @Test
    public void testDirect() {
        final Arithmetic arithmetic = new Add(new Add(1, 2), new Add(3, 4));
        final String input = infixWriter.write(arithmetic);
        final String actual = converter.toPostfix(input);
        final String expected = postfixWriter.write(arithmetic);
        assertEquals(expected, actual);
    }

}

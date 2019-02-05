package io.github.klsmith.ifpfc;

import static io.github.klsmith.ifpfc.TestHelper.withSpaces;
import static org.junit.Assert.assertEquals;
import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.doubles;
import static org.quicktheories.generators.SourceDSL.integers;

import java.util.function.BiFunction;

import org.junit.Test;

import io.github.klsmith.ifpfc.arithmetic.Add;
import io.github.klsmith.ifpfc.arithmetic.Arithmetic;
import io.github.klsmith.ifpfc.arithmetic.Divide;
import io.github.klsmith.ifpfc.arithmetic.Multiply;
import io.github.klsmith.ifpfc.arithmetic.Subtract;

public class InfixToPostfixConverterTest {

    private final InfixToPostfixConverter converter = new InfixToPostfixConverter();
    private final ArithmeticStringWriter infixWriter = new InfixArithmeticStringWriter();
    private final ArithmeticStringWriter postfixWriter = new PostfixArithmeticStringWriter();

    private void testSimple(BiFunction<Arithmetic, Arithmetic, Arithmetic> constructor,
            String operator) {
        qt().forAll(integers().all(), integers().all(), integers().all(), integers().all())
                .checkAssert((a, b, c, d) -> {
                    final String input = withSpaces(a, "+", b, "+", c, "+", d);
                    final String actual = converter.toPostfix(input);
                    final String expected = withSpaces(a, b, "+", c, "+", d, "+");
                    assertEquals(expected, actual);
                });
        qt().forAll(doubles().any(), doubles().any(), doubles().any(), doubles().any())
                .assuming(TestHelper::assumeFiniteDoubles)
                .checkAssert((a, b, c, d) -> {
                    final String input = withSpaces(a, "+", b, "+", c, "+", d);
                    final String actual = converter.toPostfix(input);
                    final String expected = withSpaces(a, b, "+", c, "+", d, "+");
                    assertEquals(expected, actual);
                });
    }

    @Test
    public void testSimpleAddition() {
        testSimple(Add::new, Add.SYMBOL);
    }

    @Test
    public void testSimpleSubtraction() {
        testSimple(Subtract::new, Subtract.SYMBOL);
    }

    @Test
    public void testSimpleMultiplication() {
        testSimple(Multiply::new, Multiply.SYMBOL);
    }

    @Test
    public void testSimpleDivision() {
        testSimple(Divide::new, Divide.SYMBOL);
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

package io.github.klsmith.ifpfc;

import static io.github.klsmith.ifpfc.util.TestHelper.withSpaces;
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
import io.github.klsmith.ifpfc.arithmetic.Value;
import io.github.klsmith.ifpfc.util.TestHelper;

public class InfixArithmeticParserTest {

    private final ArithmeticParser parser = new InfixArithmeticParser();

    /* Abstract Testing Methods */

    public void testSimple(BiFunction<Arithmetic, Arithmetic, Arithmetic> constructor, String operator) {
        qt().forAll(integers().all(), integers().all())
                .checkAssert((a, b) -> {
                    final Arithmetic expected = constructor.apply(
                            new Value(a),
                            new Value(b));
                    final Arithmetic actual = parser.parse(withSpaces(a, operator, b));
                    assertEquals(expected, actual);
                });
        qt().forAll(doubles().any(), doubles().any())
                .assuming(TestHelper::assumeFiniteDoubles)
                .checkAssert((a, b) -> {
                    final Arithmetic expected = constructor.apply(
                            new Value(a),
                            new Value(b));
                    final Arithmetic actual = parser.parse(withSpaces(a, operator, b));
                    assertEquals(expected, actual);
                });
    }

    private void testSingleNested(BiFunction<Arithmetic, Arithmetic, Arithmetic> constructor, String operator) {
        qt().forAll(integers().all(), integers().all(), integers().all())
                .checkAssert((a, b, c) -> {
                    final Arithmetic expected = constructor.apply(
                            constructor.apply(new Value(a), new Value(b)),
                            new Value(c));
                    final Arithmetic actual = parser.parse(withSpaces(a, operator, b, operator, c));
                    assertEquals(expected, actual);
                });
        qt().forAll(doubles().any(), doubles().any(), doubles().any())
                .assuming(TestHelper::assumeFiniteDoubles)
                .checkAssert((a, b, c) -> {
                    final Arithmetic expected = constructor.apply(
                            constructor.apply(new Value(a), new Value(b)),
                            new Value(c));
                    final Arithmetic actual = parser.parse(withSpaces(a, operator, b, operator, c));
                    assertEquals(expected, actual);
                });
    }

    private void testDoubleNested(BiFunction<Arithmetic, Arithmetic, Arithmetic> constructor, String operator) {
        qt().forAll(integers().all(), integers().all(), integers().all(), integers().all())
                .checkAssert((a, b, c, d) -> {
                    final Arithmetic expected = constructor.apply(
                            constructor.apply(
                                    constructor.apply(
                                            new Value(a),
                                            new Value(b)),
                                    new Value(c)),
                            new Value(d));
                    final Arithmetic actual = parser.parse(withSpaces(a, operator, b, operator, c, operator, d));
                    assertEquals(expected, actual);
                });
        qt().forAll(doubles().any(), doubles().any(), doubles().any(), doubles().any())
                .assuming(TestHelper::assumeFiniteDoubles)
                .checkAssert((a, b, c, d) -> {
                    final Arithmetic expected = constructor.apply(
                            constructor.apply(
                                    constructor.apply(
                                            new Value(a),
                                            new Value(b)),
                                    new Value(c)),
                            new Value(d));
                    final Arithmetic actual = parser.parse(withSpaces(a, operator, b, operator, c, operator, d));
                    assertEquals(expected, actual);
                });
    }

    private void testDoubleNestedParenthesis(BiFunction<Arithmetic, Arithmetic, Arithmetic> constructor,
            String operator) {
        qt().forAll(integers().all(), integers().all(), integers().all(), integers().all())
                .checkAssert((a, b, c, d) -> {
                    final Arithmetic expected = constructor.apply(
                            constructor.apply(new Value(a), new Value(b)),
                            constructor.apply(new Value(c), new Value(d)));
                    final Arithmetic actual = parser.parse(
                            withSpaces("(", a, operator, b, ")",
                                    operator, "(", c, operator, d, ")"));
                    assertEquals(expected, actual);
                });
        qt().forAll(doubles().any(), doubles().any(), doubles().any(), doubles().any())
                .assuming(TestHelper::assumeFiniteDoubles)
                .checkAssert((a, b, c, d) -> {
                    final Arithmetic expected = constructor.apply(
                            constructor.apply(new Value(a), new Value(b)),
                            constructor.apply(new Value(c), new Value(d)));
                    final Arithmetic actual = parser.parse(
                            withSpaces("(", a, operator, b, ")",
                                    operator, "(", c, operator, d, ")"));
                    assertEquals(expected, actual);
                });
    }

    /* Addition Tests */

    @Test
    public void testSimpleAddition() {
        testSimple(Add::new, Add.SYMBOL);
    }

    @Test
    public void testSingleNestedAddition() {
        testSingleNested(Add::new, Add.SYMBOL);
    }

    @Test
    public void testDoubleNestedAddition() {
        testDoubleNested(Add::new, Add.SYMBOL);
    }

    @Test
    public void testDoubleNestedParenthesis() {
        testDoubleNestedParenthesis(Add::new, Add.SYMBOL);
    }

    /* Subtraction Tests */

    @Test
    public void testSimpleSubtraction() {
        testSimple(Subtract::new, Subtract.SYMBOL);
    }

    @Test
    public void testSingleNestedSubtraction() {
        testSingleNested(Subtract::new, Subtract.SYMBOL);
    }

    @Test
    public void testDoubleNestedSubtraction() {
        testDoubleNested(Subtract::new, Subtract.SYMBOL);
    }

    /* Multiplication Tests */

    @Test
    public void testSimpleMultiplication() {
        testSimple(Multiply::new, Multiply.SYMBOL);
    }

    @Test
    public void testSingleNestedMultiplication() {
        testSingleNested(Multiply::new, Multiply.SYMBOL);
    }

    @Test
    public void testDoubleNestedMultiplication() {
        testDoubleNested(Multiply::new, Multiply.SYMBOL);
    }

    /* Division Tests */

    @Test
    public void testSimpleDivision() {
        testSimple(Divide::new, Divide.SYMBOL);
    }

    @Test
    public void testSingleNestedDivision() {
        testSingleNested(Divide::new, Divide.SYMBOL);
    }

    @Test
    public void testDoubleNestedDivision() {
        testDoubleNested(Divide::new, Divide.SYMBOL);
    }

    /* Advanced Test */

    @Test
    public void testSimplePemdas() {
        qt().forAll(integers().all(), integers().all(), integers().all())
                .checkAssert((a, b, c) -> {
                    final String input = withSpaces(a, "+", b, "*", c);
                    final Arithmetic expected = new Add(a, new Multiply(b, c));
                    final Arithmetic actual = parser.parse(input);
                    assertEquals(expected, actual);
                });
    }

    @Test
    public void testParenthesisPemdas() {
        qt().forAll(integers().all(), integers().all(), integers().all(), integers().all())
                .checkAssert((a, b, c, d) -> {
                    final String input = withSpaces(a, "-", "(", b, "/", c, ")", "+", d);
                    final Arithmetic expected = new Subtract(a, new Add(new Divide(b, c), d));
                    final Arithmetic actual = parser.parse(input);
                    assertEquals(expected, actual);
                });
    }

}

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
import io.github.klsmith.ifpfc.arithmetic.Value;

public class InfixArithmeticStringWriterTest {

    private ArithmeticStringWriter writer = new InfixArithmeticStringWriter();

    /* Abstract Testing Methods */

    public void testSimple(BiFunction<Arithmetic, Arithmetic, Arithmetic> constructor, String operator) {
        qt().forAll(integers().all(), integers().all())
                .checkAssert((a, b) -> {
                    final String expected = withSpaces(a, operator, b);
                    final String actual = writer.write(constructor.apply(
                            new Value(a),
                            new Value(b)));
                    assertEquals(expected, actual);
                });
        qt().forAll(doubles().any(), doubles().any())
                .assuming(TestHelper::assumeFiniteDoubles)
                .checkAssert((a, b) -> {
                    final String expected = withSpaces(a, operator, b);
                    final String actual = writer.write(constructor.apply(
                            new Value(a),
                            new Value(b)));
                    assertEquals(expected, actual);
                });
    }

    private void testSingleNested(BiFunction<Arithmetic, Arithmetic, Arithmetic> constructor, String operator) {
        qt().forAll(integers().all(), integers().all(), integers().all())
                .checkAssert((a, b, c) -> {
                    final String expected = withSpaces(a, operator, b, operator, c);
                    final String actual = writer.write(constructor.apply(
                            constructor.apply(new Value(a), new Value(b)),
                            new Value(c)));
                    assertEquals(expected, actual);
                });
        qt().forAll(doubles().any(), doubles().any(), doubles().any())
                .assuming(TestHelper::assumeFiniteDoubles)
                .checkAssert((a, b, c) -> {
                    final String expected = withSpaces(a, operator, b, operator, c);
                    final String actual = writer.write(constructor.apply(
                            constructor.apply(new Value(a), new Value(b)),
                            new Value(c)));
                    assertEquals(expected, actual);
                });
    }

    private void testDoubleNested(BiFunction<Arithmetic, Arithmetic, Arithmetic> constructor, String operator) {
        qt().forAll(integers().all(), integers().all(), integers().all(), integers().all())
                .checkAssert((a, b, c, d) -> {
                    final String expected = withSpaces(a, operator, b, operator, c, operator, d);
                    final String actual = writer.write(constructor.apply(
                            constructor.apply(new Value(a), new Value(b)),
                            constructor.apply(new Value(c), new Value(d))));
                    assertEquals(expected, actual);
                });
        qt().forAll(doubles().any(), doubles().any(), doubles().any(), doubles().any())
                .assuming(TestHelper::assumeFiniteDoubles)
                .checkAssert((a, b, c, d) -> {
                    final String expected = withSpaces(a, operator, b, operator, c, operator, d);
                    final String actual = writer.write(constructor.apply(
                            constructor.apply(new Value(a), new Value(b)),
                            constructor.apply(new Value(c), new Value(d))));
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

}

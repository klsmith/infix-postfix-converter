package io.github.klsmith.ifpfc.arithmetic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.doubles;
import static org.quicktheories.generators.SourceDSL.integers;

import java.math.BigDecimal;
import java.util.Objects;

import org.junit.Test;

public abstract class BinaryOperatorTest {

    protected BigDecimal predict(int a, int b) {
        return predict(BigDecimal.valueOf(a), BigDecimal.valueOf(b));
    }

    protected BigDecimal predict(double a, double b) {
        return predict(BigDecimal.valueOf(a), BigDecimal.valueOf(b));
    }

    /**
     * Must override
     */
    protected abstract BigDecimal predict(BigDecimal a, BigDecimal b);

    protected BinaryOperator build(int a, int b) {
        return buildArithmetic(new Value(a), new Value(b));
    }

    protected BinaryOperator build(double a, double b) {
        return buildArithmetic(new Value(a), new Value(b));
    }

    /**
     * Must override
     */
    protected abstract BinaryOperator buildArithmetic(Arithmetic a, Arithmetic b);

    @Test
    public final void testAllIntegers() {
        qt().forAll(integers().all(), integers().all())
                .assuming(this::integerAssumptions)
                .checkAssert((a, b) -> {
                    final BigDecimal expected = predict(a, b);
                    final BigDecimal actual = build(a, b).resolve();
                    assertEquals(expected, actual);
                });
    }

    protected boolean integerAssumptions(int a, int b) {
        return true;
    }

    @Test
    public final void testAllFiniteDoubles() {
        qt().forAll(doubles().any(), doubles().any())
                .assuming(this::allFiniteDoublesAssumptions)
                .checkAssert((a, b) -> {
                    final BigDecimal expected = predict(a, b);
                    final BigDecimal actual = build(a, b).resolve();
                    assertEquals(expected, actual);
                });
    }

    private final boolean allFiniteDoublesAssumptions(double a, double b) {
        return !Double.isInfinite(a)
                && !Double.isInfinite(b)
                && doubleAssumptions(a, b);
    }

    protected boolean doubleAssumptions(double a, double b) {
        return true;
    }

    @Test
    public final void testIdentityEquals() {
        qt().forAll(integers().all(), integers().all())
                .checkAssert((a, b) -> {
                    final BinaryOperator arithmetic = build(a, b);
                    assertTrue(arithmetic.equals(arithmetic));
                });
        qt().forAll(doubles().any(), doubles().any())
                .assuming((a, b) -> Double.isFinite(a) && Double.isFinite(b))
                .checkAssert((a, b) -> {
                    final BinaryOperator arithmetic = build(a, b);
                    assertTrue(arithmetic.equals(arithmetic));
                });
    }

    @Test
    public final void testCopyEquals() {
        qt().forAll(integers().all(), integers().all())
                .checkAssert((a, b) -> {
                    final BinaryOperator arithmetic = build(a, b);
                    final BinaryOperator copy = build(a, b);
                    assertTrue(arithmetic.equals(copy));
                });
        qt().forAll(doubles().any(), doubles().any())
                .assuming((a, b) -> Double.isFinite(a) && Double.isFinite(b))
                .checkAssert((a, b) -> {
                    final BinaryOperator arithmetic = build(a, b);
                    final BinaryOperator copy = build(a, b);
                    assertTrue(arithmetic.equals(copy));
                });
    }

    @Test
    public final void testDifferentValuesNotEquals() {
        qt().forAll(integers().all(), integers().all(), integers().all(), integers().all())
                .assuming((a, b, c, d) -> !Objects.equals(a, c) && !Objects.equals(b, d))
                .checkAssert((a, b, c, d) -> {
                    final BinaryOperator ab = build(a, b);
                    final BinaryOperator cd = build(c, d);
                    assertFalse(ab.equals(cd));
                });
        qt().forAll(doubles().any(), doubles().any(), doubles().any(), doubles().any())
                .assuming((a, b, c, d) -> Double.isFinite(a) && Double.isFinite(b)
                        && Double.isFinite(c) && Double.isFinite(d)
                        && !Objects.equals(a, c) && !Objects.equals(b, d))
                .checkAssert((a, b, c, d) -> {
                    final BinaryOperator ab = build(a, b);
                    final BinaryOperator cd = build(c, d);
                    assertFalse(ab.equals(cd));
                });
    }

    @Test
    public void testNesting() {
        qt().forAll(integers().all(), integers().all(), integers().all(), integers().all())
                .assuming(this::nestingIntegerAssumptions)
                .checkAssert((a, b, c, d) -> {
                    final BinaryOperator ab = build(a, b);
                    final BinaryOperator cd = build(c, d);
                    final BinaryOperator abc = buildArithmetic(ab, new Value(c));
                    final BinaryOperator cab = buildArithmetic(new Value(c), ab);
                    final BinaryOperator abcd = buildArithmetic(ab, cd);
                    {
                        BigDecimal expected = predict(predict(a, b), BigDecimal.valueOf(c));
                        assertEquals(expected, abc.resolve());
                    }
                    {
                        BigDecimal expected = predict(BigDecimal.valueOf(c), predict(a, b));
                        assertEquals(expected, cab.resolve());
                    }
                    {
                        BigDecimal expected = predict(predict(a, b), predict(c, d));
                        assertEquals(expected, abcd.resolve());
                    }
                });
    }

    protected boolean nestingIntegerAssumptions(int a, int b, int c, int d) {
        return true;
    }

}

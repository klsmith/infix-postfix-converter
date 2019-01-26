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

public abstract class BinaryArithmeticTest {

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

    protected BinaryArithmetic buildArithmetic(int a, int b) {
        return buildArithmetic(BigDecimal.valueOf(a), BigDecimal.valueOf(b));
    }

    protected BinaryArithmetic buildArithmetic(double a, double b) {
        return buildArithmetic(BigDecimal.valueOf(a), BigDecimal.valueOf(b));
    }

    /**
     * Must override
     */
    protected abstract BinaryArithmetic buildArithmetic(BigDecimal a, BigDecimal b);

    @Test
    public final void testAllIntegers() {
        qt().forAll(integers().all(), integers().all())
                .assuming(this::integerAssumptions)
                .checkAssert((a, b) -> {
                    final BigDecimal expected = predict(a, b);
                    final BigDecimal actual = buildArithmetic(a, b).resolve();
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
                    final BigDecimal actual = buildArithmetic(a, b).resolve();
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
                    final BinaryArithmetic arithmetic = buildArithmetic(a, b);
                    assertTrue(arithmetic.equals(arithmetic));
                });
        qt().forAll(doubles().any(), doubles().any())
                .assuming((a, b) -> Double.isFinite(a) && Double.isFinite(b))
                .checkAssert((a, b) -> {
                    final BinaryArithmetic arithmetic = buildArithmetic(a, b);
                    assertTrue(arithmetic.equals(arithmetic));
                });
    }

    @Test
    public final void testCopyEquals() {
        qt().forAll(integers().all(), integers().all())
                .checkAssert((a, b) -> {
                    final BinaryArithmetic arithmetic = buildArithmetic(a, b);
                    final BinaryArithmetic copy = buildArithmetic(a, b);
                    assertTrue(arithmetic.equals(copy));
                });
        qt().forAll(doubles().any(), doubles().any())
                .assuming((a, b) -> Double.isFinite(a) && Double.isFinite(b))
                .checkAssert((a, b) -> {
                    final BinaryArithmetic arithmetic = buildArithmetic(a, b);
                    final BinaryArithmetic copy = buildArithmetic(a, b);
                    assertTrue(arithmetic.equals(copy));
                });
    }

    @Test
    public final void testDifferentValuesNotEquals() {
        qt().forAll(integers().all(), integers().all(), integers().all(), integers().all())
                .assuming((a, b, c, d) -> !Objects.equals(a, c) && !Objects.equals(b, d))
                .checkAssert((a, b, c, d) -> {
                    final BinaryArithmetic ab = buildArithmetic(a, b);
                    final BinaryArithmetic cd = buildArithmetic(c, d);
                    assertFalse(ab.equals(cd));
                });
        qt().forAll(doubles().any(), doubles().any(), doubles().any(), doubles().any())
                .assuming((a, b, c, d) -> Double.isFinite(a) && Double.isFinite(b)
                        && Double.isFinite(c) && Double.isFinite(d))
                .checkAssert((a, b, c, d) -> {
                    final BinaryArithmetic ab = buildArithmetic(a, b);
                    final BinaryArithmetic cd = buildArithmetic(c, d);
                    assertFalse(ab.equals(cd));
                });
    }

}

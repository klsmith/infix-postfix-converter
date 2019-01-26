package io.github.klsmith.ifpfc.arithmetic;

import static org.junit.Assert.assertEquals;
import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.doubles;
import static org.quicktheories.generators.SourceDSL.integers;

import java.math.BigDecimal;

import org.quicktheories.core.Gen;

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

    protected void testAllIntegers() {
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

    protected void testAllFiniteDoubles() {
        final Gen<Double> allDoubles = doubles().any();
        qt().forAll(allDoubles, allDoubles)
                .assuming(this::allFiniteDoublesAssumptions)
                .checkAssert((a, b) -> {
                    final BigDecimal expected = predict(a, b);
                    final BigDecimal actual = buildArithmetic(a, b).resolve();
                    assertEquals(expected, actual);
                });;
    }

    private final boolean allFiniteDoublesAssumptions(double a, double b) {
        return !Double.isInfinite(a)
                && !Double.isInfinite(b)
                && doubleAssumptions(a, b);
    }

    protected boolean doubleAssumptions(double a, double b) {
        return true;
    }

}

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
                .asWithPrecursor(this::predict)
                .checkAssert((a, b, expected) -> {
                    final BigDecimal actual = buildArithmetic(a, b).resolve();
                    assertEquals(expected, actual);
                });
    }

    protected void testAllFiniteDoubles() {
        final Gen<Double> allDoubles = doubles().between(Double.MIN_VALUE, Double.MAX_VALUE);
        qt().forAll(allDoubles, allDoubles)
                .asWithPrecursor(this::predict)
                .checkAssert((a, b, expected) -> {
                    final BigDecimal actual = buildArithmetic(a, b).resolve();
                    assertEquals(expected, actual);
                });;
    }

}

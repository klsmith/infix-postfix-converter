package io.github.klsmith.ifpfc.arithmetic;

import static org.junit.Assert.assertEquals;
import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.doubles;
import static org.quicktheories.generators.SourceDSL.integers;

import java.math.BigDecimal;

import org.junit.Test;
import org.quicktheories.core.Gen;

public class SubtractTest {

    @Test
    public void testAllIntegerSubtract() {
        qt().forAll(integers().all(), integers().all())
                .asWithPrecursor((a, b) -> new BigDecimal(a).subtract(new BigDecimal(b)))
                .checkAssert((a, b, expected) -> {
                    final BigDecimal actual = new Subtract(a, b).resolve();
                    assertEquals(expected, actual);
                });
    }

    @Test
    public void testAllFiniteDoubleSubtraction() {
        final Gen<Double> allDoubles = doubles().between(Double.MIN_VALUE, Double.MAX_VALUE);
        qt().forAll(allDoubles, allDoubles)
                .asWithPrecursor((a, b) -> new BigDecimal(a).subtract(new BigDecimal(b)))
                .checkAssert((a, b, expected) -> {
                    final BigDecimal actual = new Subtract(a, b).resolve();
                    assertEquals(expected, actual);
                });;
    }

}

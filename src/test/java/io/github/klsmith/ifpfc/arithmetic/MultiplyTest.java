package io.github.klsmith.ifpfc.arithmetic;

import static org.junit.Assert.assertEquals;
import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.doubles;
import static org.quicktheories.generators.SourceDSL.integers;

import java.math.BigDecimal;

import org.junit.Test;
import org.quicktheories.core.Gen;

public class MultiplyTest {

    @Test
    public void testAllIntegerMultiplcation() {
        qt().forAll(integers().all(), integers().all())
                .asWithPrecursor((a, b) -> new BigDecimal(a).multiply(new BigDecimal(b)))
                .checkAssert((a, b, expected) -> {
                    final BigDecimal actual = new Multiply(a, b).resolve();
                    assertEquals(expected, actual);
                });
    }

    @Test
    public void testAllFiniteDoubleAddition() {
        final Gen<Double> allDoubles = doubles().between(Double.MIN_VALUE, Double.MAX_VALUE);
        qt().forAll(allDoubles, allDoubles)
                .asWithPrecursor((a, b) -> BigDecimal.valueOf(a).multiply(BigDecimal.valueOf(b)))
                .checkAssert((a, b, expected) -> {
                    final BigDecimal actual = new Multiply(a, b).resolve();
                    assertEquals(expected, actual);
                });;
    }

}

package io.github.klsmith.ifpfc.arithmetic;

import static org.junit.Assert.assertEquals;
import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.*;

import java.math.BigDecimal;

import org.junit.Test;

public class ArithmeticTest {

    @Test
    public void testAllIntegerAddition() {
        qt().forAll(integers().all(), integers().all())
                .asWithPrecursor((a, b) -> new BigDecimal(a).add(new BigDecimal(b)))
                .checkAssert((a, b, expected) -> {
                    final BigDecimal actual = new Add(a, b).resolve();
                    assertEquals(expected, actual);
                });
    }

    @Test
    public void testAllPositiveIntegerAdditionStaysPositive() {
        qt().forAll(integers().allPositive(), integers().allPositive())
                .check((a, b) -> new Add(a, b).resolve()
                        .compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    public void testAllNegativeIntegerAdditionsStayNegative() {
        qt().forAll(integers().allPositive(), integers().allPositive())
                .check((a, b) -> new Add(-a, -b).resolve()
                        .compareTo(BigDecimal.ZERO) < 0);
    }

    @Test
    public void testAllIntegerSubtract() {
        qt().forAll(integers().all(), integers().all())
                .asWithPrecursor((a, b) -> new BigDecimal(a).subtract(new BigDecimal(b)))
                .checkAssert((a, b, expected) -> {
                    final BigDecimal actual = new Subtract(a, b).resolve();
                    assertEquals(expected, actual);
                });
    }

}

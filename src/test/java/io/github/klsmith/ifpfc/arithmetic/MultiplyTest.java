package io.github.klsmith.ifpfc.arithmetic;

import static org.junit.Assert.assertEquals;
import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.integers;

import java.math.BigDecimal;

import org.junit.Test;

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

}

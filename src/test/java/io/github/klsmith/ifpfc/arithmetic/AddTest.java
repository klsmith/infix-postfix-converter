package io.github.klsmith.ifpfc.arithmetic;

import static org.junit.Assert.assertEquals;
import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.doubles;
import static org.quicktheories.generators.SourceDSL.integers;

import java.math.BigDecimal;

import org.junit.Test;
import org.quicktheories.core.Gen;

public class AddTest {

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
    public void testAllFiniteDoubleAddition() {
        final Gen<Double> allDoubles = doubles().between(Double.MIN_VALUE, Double.MAX_VALUE);
        qt().forAll(allDoubles, allDoubles)
                .asWithPrecursor((a, b) -> new BigDecimal(a).add(new BigDecimal(b)))
                .checkAssert((a, b, expected) -> {
                    final BigDecimal actual = new Add(a, b).resolve();
                    assertEquals(expected, actual);
                });;
    }

    @Test
    public void testAllPositiveIntegerAdditionStaysPositive() {
        qt().forAll(integers().allPositive(), integers().allPositive())
                .check((a, b) -> new Add(a, b).resolve()
                        .compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    public void testAllPositiveDoubleAdditionStaysPositive() {
        final Gen<Double> allPositiveDoubles = doubles().between(Double.MIN_VALUE, Double.MAX_VALUE);
        qt().forAll(allPositiveDoubles, allPositiveDoubles)
                .check((a, b) -> new Add(a, b).resolve()
                        .compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    public void testAllNegativeIntegerAdditionsStayNegative() {
        final Gen<Integer> allNegativeIntegers = integers().between(Integer.MIN_VALUE, -1);
        qt().forAll(allNegativeIntegers, allNegativeIntegers)
                .check((a, b) -> new Add(a, b).resolve()
                        .compareTo(BigDecimal.ZERO) < 0);
    }

    @Test
    public void testAllNegativeDoubleAdditionsStayNegative() {
        final Gen<Double> allNegativeDoubles = doubles().between(-Double.MAX_VALUE, 0);
        qt().forAll(allNegativeDoubles, allNegativeDoubles)
                .assuming((a, b) -> a + b != 0)
                .check((a, b) -> new Add(a, b).resolve()
                        .compareTo(BigDecimal.ZERO) < 0);
    }

}

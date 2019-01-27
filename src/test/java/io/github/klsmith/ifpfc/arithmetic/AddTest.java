package io.github.klsmith.ifpfc.arithmetic;

import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.doubles;
import static org.quicktheories.generators.SourceDSL.integers;

import java.math.BigDecimal;

import org.junit.Test;
import org.quicktheories.core.Gen;

public class AddTest extends BinaryArithmeticTest {

    @Override
    protected BigDecimal predict(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    @Override
    protected BinaryArithmetic buildArithmetic(Arithmetic a, Arithmetic b) {
        return new Add(a, b);
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

    // @Test
    // public void testNestedAddition() {
    // qt().forAll(integers().all(), integers().all(), integers().all(),
    // integers().all())
    // .checkAssert((a, b, c, d) -> {
    // final Add addAB = new Add(a, b);
    // final Add addCD = new Add(c, d);
    // final Add addABC = new Add(addAB, c);
    // final Add addCAB = new Add(c, addAB);
    // final Add addABCD = new Add(addAB, addCD);
    // {
    // BigDecimal expected = addAB.resolve().add(BigDecimal.valueOf(c));
    // assertEquals(expected, addABC.resolve());
    // assertEquals(expected, addCAB.resolve());
    // }
    // {
    // BigDecimal expected = addABC.resolve().add(BigDecimal.valueOf(d));
    // assertEquals(expected, addABCD.resolve());
    // }
    // });
    // }

}

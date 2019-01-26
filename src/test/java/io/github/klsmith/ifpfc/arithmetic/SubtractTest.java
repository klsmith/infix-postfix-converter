package io.github.klsmith.ifpfc.arithmetic;

import java.math.BigDecimal;

import org.junit.Test;

public class SubtractTest extends BinaryArithmeticTest {

    @Override
    protected BigDecimal predict(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }

    @Override
    protected BinaryArithmetic buildArithmetic(BigDecimal a, BigDecimal b) {
        return new Subtract(a, b);
    }

    @Test
    public void testAllIntegerSubtraction() {
        testAllIntegers();
    }

    @Test
    public void testAllFiniteDoubleSubtraction() {
        testAllFiniteDoubles();
    }

}

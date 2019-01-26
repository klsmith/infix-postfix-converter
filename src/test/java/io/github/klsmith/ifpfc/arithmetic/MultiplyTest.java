package io.github.klsmith.ifpfc.arithmetic;

import java.math.BigDecimal;

public class MultiplyTest extends BinaryArithmeticTest {

    @Override
    protected BigDecimal predict(BigDecimal a, BigDecimal b) {
        return a.multiply(b);
    }

    @Override
    protected BinaryArithmetic buildArithmetic(BigDecimal a, BigDecimal b) {
        return new Multiply(a, b);
    }

}

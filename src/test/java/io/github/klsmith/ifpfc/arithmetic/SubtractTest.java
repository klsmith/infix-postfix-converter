package io.github.klsmith.ifpfc.arithmetic;

import java.math.BigDecimal;

public class SubtractTest extends BinaryArithmeticTest {

    @Override
    protected BigDecimal predict(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }

    @Override
    protected BinaryArithmetic buildArithmetic(Arithmetic a, Arithmetic b) {
        return new Subtract(a, b);
    }

}

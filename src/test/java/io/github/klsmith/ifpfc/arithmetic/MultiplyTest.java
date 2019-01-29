package io.github.klsmith.ifpfc.arithmetic;

import java.math.BigDecimal;

public class MultiplyTest extends BinaryOperatorTest {

    @Override
    protected BigDecimal predict(BigDecimal a, BigDecimal b) {
        return a.multiply(b);
    }

    @Override
    protected BinaryOperator buildArithmetic(Arithmetic a, Arithmetic b) {
        return new Multiply(a, b);
    }

}

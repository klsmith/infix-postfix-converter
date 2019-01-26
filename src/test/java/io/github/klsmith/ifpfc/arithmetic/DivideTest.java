package io.github.klsmith.ifpfc.arithmetic;

import java.math.BigDecimal;

public class DivideTest extends BinaryArithmeticTest {

    @Override
    protected BigDecimal predict(BigDecimal a, BigDecimal b) {
        return a.divide(b, Divide.DEFAULT_ROUNDING_MODE);
    }

    @Override
    protected BinaryArithmetic buildArithmetic(BigDecimal a, BigDecimal b) {
        return new Divide(a, b);
    }

    @Override
    protected boolean integerAssumptions(int a, int b) {
        return b != 0;
    }

    @Override
    protected boolean doubleAssumptions(double a, double b) {
        return b != 0;
    }

}

package io.github.klsmith.ifpfc.arithmetic;

import java.math.BigDecimal;

public class DivideTest extends BinaryOperatorTest {

    @Override
    protected BigDecimal predict(BigDecimal a, BigDecimal b) {
        return a.divide(b, Divide.DEFAULT_ROUNDING_MODE);
    }

    @Override
    protected BinaryOperator buildArithmetic(Arithmetic a, Arithmetic b) {
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

    @Override
    protected boolean nestingIntegerAssumptions(int a, int b, int c, int d) {
        return a != 0 && b != 0 && c != 0 && d != 0;
    }

}

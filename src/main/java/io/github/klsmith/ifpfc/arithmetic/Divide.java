package io.github.klsmith.ifpfc.arithmetic;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Divide extends BinaryArithmetic {

    public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.UP;

    public Divide(int a, int b) {
        super(a, b);
    }

    public Divide(double a, double b) {
        super(a, b);
    }

    public Divide(BigDecimal a, BigDecimal b) {
        super(a, b);
    }

    @Override
    public BigDecimal resolve() {
        return getA().divide(getB(), DEFAULT_ROUNDING_MODE);
    }

}

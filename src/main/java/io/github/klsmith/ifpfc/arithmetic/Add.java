package io.github.klsmith.ifpfc.arithmetic;

import java.math.BigDecimal;

public final class Add extends BinaryArithmetic {

    public Add(int a, int b) {
        super(a, b);
    }

    public Add(double a, double b) {
        super(a, b);
    }

    public Add(BigDecimal a, BigDecimal b) {
        super(a, b);
    }

    @Override
    public BigDecimal resolve() {
        return getA().add(getB());
    }

}

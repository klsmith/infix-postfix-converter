package io.github.klsmith.ifpfc.arithmetic;

import java.math.BigDecimal;

public class Subtract extends BinaryArithmetic {

    public Subtract(int a, int b) {
        super(a, b);
    }

    public Subtract(double a, double b) {
        super(a, b);
    }

    public Subtract(BigDecimal a, BigDecimal b) {
        super(a, b);
    }

    @Override
    protected BigDecimal resolve(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }

}

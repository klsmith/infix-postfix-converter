package io.github.klsmith.ifpfc.arithmetic;

import java.math.BigDecimal;

public class Multiply extends BinaryArithmetic {

    public Multiply(int a, int b) {
        super(a, b);
    }

    public Multiply(double a, double b) {
        super(a, b);
    }

    public Multiply(BigDecimal a, BigDecimal b) {
        super(a, b);
    }

    @Override
    protected BigDecimal resolve(BigDecimal a, BigDecimal b) {
        return a.multiply(b);
    }

}

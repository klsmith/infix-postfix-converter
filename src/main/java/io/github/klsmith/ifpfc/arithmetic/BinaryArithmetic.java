package io.github.klsmith.ifpfc.arithmetic;

import java.math.BigDecimal;

public abstract class BinaryArithmetic implements Arithmetic {

    private final BigDecimal a, b;

    public BinaryArithmetic(int a, int b) {
        this(BigDecimal.valueOf(a), BigDecimal.valueOf(b));
    }

    public BinaryArithmetic(double a, double b) {
        this(BigDecimal.valueOf(a), BigDecimal.valueOf(b));
    }

    public BinaryArithmetic(BigDecimal a, BigDecimal b) {
        this.a = a;
        this.b = b;
    }

    protected BigDecimal getA() {
        return a;
    }

    protected BigDecimal getB() {
        return b;
    }

}

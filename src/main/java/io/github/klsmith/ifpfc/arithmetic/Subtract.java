package io.github.klsmith.ifpfc.arithmetic;

import java.math.BigDecimal;

public class Subtract implements Arithmetic {

    final BigDecimal a, b;

    public Subtract(double a, double b) {
        this(new BigDecimal(a), new BigDecimal(b));
    }

    public Subtract(BigDecimal a, BigDecimal b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public BigDecimal resolve() {
        return a.subtract(b);
    }

}

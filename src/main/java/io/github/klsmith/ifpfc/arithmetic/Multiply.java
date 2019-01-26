package io.github.klsmith.ifpfc.arithmetic;

import java.math.BigDecimal;

public class Multiply implements Arithmetic {

    private final BigDecimal a, b;

    public Multiply(int a, int b) {
        this.a = BigDecimal.valueOf(a);
        this.b = BigDecimal.valueOf(b);
    }

    public Multiply(double a, double b) {
        this.a = BigDecimal.valueOf(a);
        this.b = BigDecimal.valueOf(b);
    }

    @Override
    public BigDecimal resolve() {
        return a.multiply(b);
    }

}

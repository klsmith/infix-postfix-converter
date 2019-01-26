package io.github.klsmith.ifpfc.arithmetic;

import java.math.BigDecimal;

public class Subtract implements Arithmetic {

    final BigDecimal a, b;

    public Subtract(int a, int b) {
        this.a = BigDecimal.valueOf(a);
        this.b = BigDecimal.valueOf(b);
    }

    public Subtract(double a, double b) {
        this.a = BigDecimal.valueOf(a);
        this.b = BigDecimal.valueOf(b);
    }

    @Override
    public BigDecimal resolve() {
        return a.subtract(b);
    }

}

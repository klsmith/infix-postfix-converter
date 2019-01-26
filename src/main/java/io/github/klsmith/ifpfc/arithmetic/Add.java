package io.github.klsmith.ifpfc.arithmetic;

import java.math.BigDecimal;

public final class Add implements Arithmetic {

    private final BigDecimal a, b;

    public Add(int a, int b) {
        this.a = BigDecimal.valueOf(a);
        this.b = BigDecimal.valueOf(b);
    }

    public Add(double a, double b) {
        this.a = BigDecimal.valueOf(a);
        this.b = BigDecimal.valueOf(b);
    }

    @Override
    public BigDecimal resolve() {
        return a.add(b);
    }

}

package io.github.klsmith.ifpfc.arithmetic;

import java.math.BigDecimal;

public final class Add implements Arithmetic {

    private BigDecimal a, b;

    public Add(int a, int b) {
        this(new BigDecimal(a), new BigDecimal(b));
    }

    public Add(BigDecimal a, BigDecimal b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public BigDecimal resolve() {
        return a.add(b);
    }

}

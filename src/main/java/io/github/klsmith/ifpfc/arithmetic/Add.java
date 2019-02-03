package io.github.klsmith.ifpfc.arithmetic;

import java.math.BigDecimal;

public final class Add extends BinaryOperator {

    public static final String SYMBOL = "+";

    public Add(int a, int b) {
        super(a, b);
    }

    public Add(double a, double b) {
        super(a, b);
    }

    public Add(BigDecimal a, BigDecimal b) {
        super(a, b);
    }

    public Add(Arithmetic a, int b) {
        super(a, b);
    }

    public Add(int a, Arithmetic b) {
        super(a, b);
    }

    public Add(Arithmetic a, double b) {
        super(a, b);
    }

    public Add(double a, Arithmetic b) {
        super(a, b);
    }

    public Add(Arithmetic a, Arithmetic b) {
        super(a, b);
    }

    @Override
    protected BigDecimal resolve(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    @Override
    public String getSymbol() {
        return SYMBOL;
    }

    @Override
    public String toString() {
        return "Add(" + getA() + ", " + getB() + ")";
    }

}

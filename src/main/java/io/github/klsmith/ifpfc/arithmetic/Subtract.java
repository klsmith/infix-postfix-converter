package io.github.klsmith.ifpfc.arithmetic;

import java.math.BigDecimal;

public class Subtract extends BinaryOperator {

    public static final String SYMBOL = "-";

    public Subtract(int a, int b) {
        super(a, b);
    }

    public Subtract(double a, double b) {
        super(a, b);
    }

    public Subtract(BigDecimal a, BigDecimal b) {
        super(a, b);
    }

    public Subtract(Arithmetic a, int b) {
        super(a, b);
    }

    public Subtract(int a, Arithmetic b) {
        super(a, b);
    }

    public Subtract(Arithmetic a, double b) {
        super(a, b);
    }

    public Subtract(double a, Arithmetic b) {
        super(a, b);
    }

    public Subtract(Arithmetic a, Arithmetic b) {
        super(a, b);
    }

    @Override
    protected BigDecimal resolve(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }

    @Override
    public String getSymbol() {
        return SYMBOL;
    }

    @Override
    public String toString() {
        return "Subtract(" + getA() + ", " + getB() + ")";
    }

}

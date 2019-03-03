package io.github.klsmith.ifpfc.arithmetic;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Divide extends BinaryOperator {

    public static final String SYMBOL = "/";
    public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.UP;

    public Divide(int a, int b) {
        super(a, b);
    }

    public Divide(double a, double b) {
        super(a, b);
    }

    public Divide(BigDecimal a, BigDecimal b) {
        super(a, b);
    }

    public Divide(Arithmetic a, int b) {
        super(a, b);
    }

    public Divide(int a, Arithmetic b) {
        super(a, b);
    }

    public Divide(Arithmetic a, double b) {
        super(a, b);
    }

    public Divide(double a, Arithmetic b) {
        super(a, b);
    }

    public Divide(Arithmetic a, Arithmetic b) {
        super(a, b);
    }

    @Override
    protected BigDecimal resolve(BigDecimal a, BigDecimal b) {
        return a.divide(b, DEFAULT_ROUNDING_MODE);
    }

    @Override
    public String getSymbol() {
        return SYMBOL;
    }

    @Override
    public String toString() {
        return "Divide(" + getA() + ", " + getB() + ")";
    }

}

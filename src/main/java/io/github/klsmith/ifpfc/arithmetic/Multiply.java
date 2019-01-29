package io.github.klsmith.ifpfc.arithmetic;

import java.math.BigDecimal;

public class Multiply extends BinaryOperator {

    public Multiply(int a, int b) {
        super(a, b);
    }

    public Multiply(double a, double b) {
        super(a, b);
    }

    public Multiply(BigDecimal a, BigDecimal b) {
        super(a, b);
    }

    public Multiply(Arithmetic a, int b) {
        super(a, b);
    }

    public Multiply(int a, Arithmetic b) {
        super(a, b);
    }

    public Multiply(Arithmetic a, double b) {
        super(a, b);
    }

    public Multiply(double a, Arithmetic b) {
        super(a, b);
    }

    public Multiply(Arithmetic a, Arithmetic b) {
        super(a, b);
    }

    @Override
    protected BigDecimal resolve(BigDecimal a, BigDecimal b) {
        return a.multiply(b);
    }

}

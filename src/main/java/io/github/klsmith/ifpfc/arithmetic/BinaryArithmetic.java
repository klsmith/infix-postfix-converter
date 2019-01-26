package io.github.klsmith.ifpfc.arithmetic;

import java.math.BigDecimal;

public abstract class BinaryArithmetic implements Arithmetic {

    private final Arithmetic a, b;

    public BinaryArithmetic(int a, int b) {
        this(new Value(a), new Value(b));
    }

    public BinaryArithmetic(double a, double b) {
        this(new Value(a), new Value(b));
    }

    public BinaryArithmetic(BigDecimal a, BigDecimal b) {
        this(new Value(a), new Value(b));
    }

    public BinaryArithmetic(Arithmetic a, int b) {
        this(a, new Value(b));
    }

    public BinaryArithmetic(int a, Arithmetic b) {
        this(new Value(a), b);
    }

    public BinaryArithmetic(Arithmetic a, double b) {
        this(a, new Value(b));
    }

    public BinaryArithmetic(double a, Arithmetic b) {
        this(new Value(a), b);
    }

    public BinaryArithmetic(Arithmetic a, Arithmetic b) {
        this.a = a;
        this.b = b;
    }

    protected Arithmetic getA() {
        return a;
    }

    protected Arithmetic getB() {
        return b;
    }

    @Override
    public final BigDecimal resolve() {
        return resolve(getA().resolve(), getB().resolve());
    }

    protected abstract BigDecimal resolve(BigDecimal a, BigDecimal b);

}

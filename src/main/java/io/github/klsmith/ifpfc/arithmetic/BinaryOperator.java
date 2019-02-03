package io.github.klsmith.ifpfc.arithmetic;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class BinaryOperator implements Arithmetic {

    private final Arithmetic a, b;

    public BinaryOperator(int a, int b) {
        this(new Value(a), new Value(b));
    }

    public BinaryOperator(double a, double b) {
        this(new Value(a), new Value(b));
    }

    public BinaryOperator(BigDecimal a, BigDecimal b) {
        this(new Value(a), new Value(b));
    }

    public BinaryOperator(Arithmetic a, int b) {
        this(a, new Value(b));
    }

    public BinaryOperator(int a, Arithmetic b) {
        this(new Value(a), b);
    }

    public BinaryOperator(Arithmetic a, double b) {
        this(a, new Value(b));
    }

    public BinaryOperator(double a, Arithmetic b) {
        this(new Value(a), b);
    }

    public BinaryOperator(Arithmetic a, Arithmetic b) {
        this.a = a;
        this.b = b;
    }

    public Arithmetic getA() {
        return a;
    }

    public Arithmetic getB() {
        return b;
    }

    @Override
    public final BigDecimal resolve() {
        return resolve(getA().resolve(), getB().resolve());
    }

    protected abstract BigDecimal resolve(BigDecimal a, BigDecimal b);

    @Override
    public int hashCode() {
        return Objects.hash(getA(), getB());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass().equals(getClass())) {
            final BinaryOperator other = (BinaryOperator) obj;
            return Objects.equals(getA(), other.getA())
                    && Objects.equals(getB(), other.getB());
        }
        return false;
    }

}

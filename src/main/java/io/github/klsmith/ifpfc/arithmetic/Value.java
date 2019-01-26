package io.github.klsmith.ifpfc.arithmetic;

import java.math.BigDecimal;
import java.util.Objects;

public class Value implements Arithmetic {

    private final BigDecimal value;

    public Value(int value) {
        this(BigDecimal.valueOf(value));
    }

    public Value(double value) {
        this(BigDecimal.valueOf(value));
    }

    public Value(BigDecimal value) {
        this.value = value;
    }

    @Override
    public BigDecimal resolve() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Value) {
            final Value other = (Value) obj;
            return Objects.equals(value, other.value);
        }
        return false;
    }

}

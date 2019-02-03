package io.github.klsmith.ifpfc;

import io.github.klsmith.ifpfc.arithmetic.Add;
import io.github.klsmith.ifpfc.arithmetic.Arithmetic;
import io.github.klsmith.ifpfc.arithmetic.Value;

public class PostfixArithmeticStringWriter implements ArithmeticStringWriter {

    @Override
    public String write(Arithmetic arithmetic) {
        final StringBuilder builder = new StringBuilder();
        write(builder, arithmetic);
        return builder.toString();
    }

    private void write(StringBuilder builder, Arithmetic arithmetic) {
        if (arithmetic instanceof Value) {
            final Value value = (Value) arithmetic;
            builder.append(value);
        }
        if (arithmetic instanceof Add) {
            final Add add = (Add) arithmetic;
            builder.append(String.join(" ", write(add.getA()), write(add.getB()), "+"));
        }
    }

}

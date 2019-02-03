package io.github.klsmith.ifpfc;

import io.github.klsmith.ifpfc.arithmetic.Add;
import io.github.klsmith.ifpfc.arithmetic.Arithmetic;
import io.github.klsmith.ifpfc.arithmetic.Value;

public class PostfixArithmeticStringWriter implements ArithmeticStringWriter {

    @Override
    public String write(Arithmetic arithmetic) {
        if (arithmetic instanceof Value) {
            final Value value = (Value) arithmetic;
            return value.toString();
        }
        if (arithmetic instanceof Add) {
            final Add add = (Add) arithmetic;
            return String.join(" ", write(add.getA()), write(add.getB()), "+");
        }
        return "";
    }

}

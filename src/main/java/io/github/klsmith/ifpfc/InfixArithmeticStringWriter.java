package io.github.klsmith.ifpfc;

import io.github.klsmith.ifpfc.arithmetic.Arithmetic;
import io.github.klsmith.ifpfc.arithmetic.BinaryOperator;
import io.github.klsmith.ifpfc.arithmetic.Value;

public class InfixArithmeticStringWriter implements ArithmeticStringWriter {

    @Override
    public String write(Arithmetic arithmetic) {
        if (arithmetic instanceof Value) {
            final Value value = (Value) arithmetic;
            return value.toString();
        }
        if (arithmetic instanceof BinaryOperator) {
            final BinaryOperator binaryOperator = (BinaryOperator) arithmetic;
            return String.join(" ",
                    writeNested(binaryOperator.getA()),
                    binaryOperator.getSymbol(),
                    writeNested(binaryOperator.getB()));
        }
        return "";
    }

    public String writeNested(Arithmetic arithmetic) {
        if (arithmetic instanceof Value) {
            final Value value = (Value) arithmetic;
            return value.toString();
        }
        if (arithmetic instanceof BinaryOperator) {
            final BinaryOperator binaryOperator = (BinaryOperator) arithmetic;
            return String.join(" ",
                    "(",
                    writeNested(binaryOperator.getA()),
                    binaryOperator.getSymbol(),
                    writeNested(binaryOperator.getB()),
                    ")");
        }
        return "";
    }

}

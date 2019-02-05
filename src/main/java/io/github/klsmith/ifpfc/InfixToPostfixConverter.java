package io.github.klsmith.ifpfc;

import io.github.klsmith.ifpfc.arithmetic.Arithmetic;

public class InfixToPostfixConverter {

    public String toPostfix(String infix) {
        final ArithmeticParser infixParser = new InfixArithmeticParser();
        final Arithmetic arithmetic = infixParser.parse(infix);
        final ArithmeticStringWriter writer = new PostfixArithmeticStringWriter();
        return writer.write(arithmetic);
    }

}

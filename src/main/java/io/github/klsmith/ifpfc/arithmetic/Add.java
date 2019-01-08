package io.github.klsmith.ifpfc.arithmetic;

import java.math.BigDecimal;

public class Add implements Arithmetic {

    public Add(int i, int j) {}

    @Override
    public BigDecimal resolve() {
        return new BigDecimal(9);
    }

}

package io.github.klsmith.ifpfc.arithmetic;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class ArithmeticTest {

    @Test
    public void test_add_4_and_5_is_9() {
        final Arithmetic arithmetic = new Add(4, 5);
        assertEquals(new BigDecimal(9), arithmetic.resolve());
    }

    @Test
    public void test_add_4_and_4_is_8() {
        final Arithmetic arithmetic = new Add(4, 4);
        assertEquals(new BigDecimal(8), arithmetic.resolve());
    }

}

package io.github.klsmith.ifpfc.arithmetic;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class ArithmeticTest {

    @Test
    public void test_add_4_and_5_is_9() {
        final BigDecimal expected = new BigDecimal(9);
        final BigDecimal actual = new Add(4, 5).resolve();
        assertEquals(expected, actual);
    }

    @Test
    public void test_add_4_and_4_is_8() {
        final BigDecimal expected = new BigDecimal(8);
        final BigDecimal actual = new Add(4, 4).resolve();
        assertEquals(expected, actual);
    }

}

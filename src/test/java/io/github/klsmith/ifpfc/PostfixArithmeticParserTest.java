package io.github.klsmith.ifpfc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.github.klsmith.ifpfc.arithmetic.Add;
import io.github.klsmith.ifpfc.arithmetic.Arithmetic;

public class PostfixArithmeticParserTest {

    @Test
    public void test_2_2_plus() {
        final ArithmeticParser parser = new PostfixArithmeticParser();
        final Arithmetic expected = new Add(2, 2);
        final Arithmetic actual = parser.parse("2 2 +");
        assertEquals(expected, actual);
    }

    @Test
    public void test_4_2_plus() {
        final ArithmeticParser parser = new PostfixArithmeticParser();
        final Arithmetic expected = new Add(4, 2);
        final Arithmetic actual = parser.parse("4 2 +");
        assertEquals(expected, actual);
    }

}

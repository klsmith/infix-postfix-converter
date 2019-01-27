package io.github.klsmith.ifpfc;

import static org.junit.Assert.assertEquals;
import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.integers;

import org.junit.Test;

import io.github.klsmith.ifpfc.arithmetic.Add;
import io.github.klsmith.ifpfc.arithmetic.Arithmetic;

public class PostfixArithmeticParserTest {

    @Test
    public void testSimpleIntegerAddition() {
        final ArithmeticParser parser = new PostfixArithmeticParser();
        qt().forAll(integers().all(), integers().all())
                .checkAssert((a, b) -> {
                    final Arithmetic expected = predictSimpleAddition(a, b);
                    final Arithmetic actual = parser.parse(toSimpleAdditionString(a, b));
                    assertEquals(expected, actual);
                });
    }

    private Arithmetic predictSimpleAddition(int a, int b) {
        return new Add(a, b);
    }

    private String toSimpleAdditionString(int a, int b) {
        return String.join(" ",
                String.valueOf(a),
                String.valueOf(b),
                "+");
    }

}

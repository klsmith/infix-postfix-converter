package io.github.klsmith.ifpfc;

import static org.junit.Assert.assertEquals;
import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.doubles;
import static org.quicktheories.generators.SourceDSL.integers;

import java.math.BigDecimal;

import org.junit.Test;

import io.github.klsmith.ifpfc.arithmetic.Add;
import io.github.klsmith.ifpfc.arithmetic.Arithmetic;

public class PostfixArithmeticParserTest {

    @Test
    public void testSimpleAddition() {
        final ArithmeticParser parser = new PostfixArithmeticParser();
        qt().forAll(integers().all(), integers().all())
                .checkAssert((a, b) -> {
                    final Arithmetic expected = new Add(a, b);
                    final Arithmetic actual = parser.parse(toSimpleAdditionString(a, b));
                    assertEquals(expected, actual);
                });
        qt().forAll(doubles().any(), doubles().any())
                .assuming(this::assumeFiniteDoubles)
                .checkAssert((a, b) -> {
                    final Arithmetic expected = new Add(a, b);
                    final Arithmetic actual = parser.parse(toSimpleAdditionString(a, b));
                    assertEquals(expected, actual);
                });
    }

    private String toSimpleAdditionString(int a, int b) {
        return toSimpleAdditionString(BigDecimal.valueOf(a), BigDecimal.valueOf(b));
    }

    private String toSimpleAdditionString(double a, double b) {
        return toSimpleAdditionString(BigDecimal.valueOf(a), BigDecimal.valueOf(b));
    }

    private String toSimpleAdditionString(BigDecimal a, BigDecimal b) {
        return String.join(" ", a.toString(), b.toString(), "+");
    }

    private boolean assumeFiniteDoubles(double a, double b) {
        return Double.isFinite(a) && Double.isFinite(b);
    }

}

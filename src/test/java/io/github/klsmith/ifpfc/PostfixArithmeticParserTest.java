package io.github.klsmith.ifpfc;

import static org.junit.Assert.assertEquals;
import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.doubles;
import static org.quicktheories.generators.SourceDSL.integers;

import java.math.BigDecimal;

import org.junit.Test;

import io.github.klsmith.ifpfc.arithmetic.Add;
import io.github.klsmith.ifpfc.arithmetic.Arithmetic;
import io.github.klsmith.ifpfc.arithmetic.Divide;
import io.github.klsmith.ifpfc.arithmetic.Multiply;
import io.github.klsmith.ifpfc.arithmetic.Subtract;

public class PostfixArithmeticParserTest {

    private String toPostfixString(int a, int b, String operator) {
        return toPostfixString(BigDecimal.valueOf(a), BigDecimal.valueOf(b), operator);
    }

    private String toPostfixString(double a, double b, String operator) {
        return toPostfixString(BigDecimal.valueOf(a), BigDecimal.valueOf(b), operator);
    }

    private String toPostfixString(BigDecimal a, BigDecimal b, String operator) {
        return String.join(" ", a.toString(), b.toString(), operator);
    }

    private boolean assumeFiniteDoubles(double a, double b) {
        return Double.isFinite(a) && Double.isFinite(b);
    }

    @Test
    public void testSimpleAddition() {
        final ArithmeticParser parser = new PostfixArithmeticParser();
        qt().forAll(integers().all(), integers().all())
                .checkAssert((a, b) -> {
                    final Arithmetic expected = new Add(a, b);
                    final Arithmetic actual = parser.parse(toPostfixString(a, b, "+"));
                    assertEquals(expected, actual);
                });
        qt().forAll(doubles().any(), doubles().any())
                .assuming(this::assumeFiniteDoubles)
                .checkAssert((a, b) -> {
                    final Arithmetic expected = new Add(a, b);
                    final Arithmetic actual = parser.parse(toPostfixString(a, b, "+"));
                    assertEquals(expected, actual);
                });
    }

    @Test
    public void testSimpleSubtraction() {
        final ArithmeticParser parser = new PostfixArithmeticParser();
        qt().forAll(integers().all(), integers().all())
                .checkAssert((a, b) -> {
                    final Arithmetic expected = new Subtract(a, b);
                    final Arithmetic actual = parser.parse(toPostfixString(a, b, "-"));
                    assertEquals(expected, actual);
                });
        qt().forAll(doubles().any(), doubles().any())
                .assuming(this::assumeFiniteDoubles)
                .checkAssert((a, b) -> {
                    final Arithmetic expected = new Subtract(a, b);
                    final Arithmetic actual = parser.parse(toPostfixString(a, b, "-"));
                    assertEquals(expected, actual);
                });
    }

    @Test
    public void testSimpleMultiplication() {
        final ArithmeticParser parser = new PostfixArithmeticParser();
        qt().forAll(integers().all(), integers().all())
                .checkAssert((a, b) -> {
                    final Arithmetic expected = new Multiply(a, b);
                    final Arithmetic actual = parser.parse(toPostfixString(a, b, "*"));
                    assertEquals(expected, actual);
                });
        qt().forAll(doubles().any(), doubles().any())
                .assuming(this::assumeFiniteDoubles)
                .checkAssert((a, b) -> {
                    final Arithmetic expected = new Multiply(a, b);
                    final Arithmetic actual = parser.parse(toPostfixString(a, b, "*"));
                    assertEquals(expected, actual);
                });
    }

    @Test
    public void testSimpleDivision() {
        final ArithmeticParser parser = new PostfixArithmeticParser();
        qt().forAll(integers().all(), integers().all())
                .checkAssert((a, b) -> {
                    final Arithmetic expected = new Divide(a, b);
                    final Arithmetic actual = parser.parse(toPostfixString(a, b, "/"));
                    assertEquals(expected, actual);
                });
        qt().forAll(doubles().any(), doubles().any())
                .assuming(this::assumeFiniteDoubles)
                .checkAssert((a, b) -> {
                    final Arithmetic expected = new Divide(a, b);
                    final Arithmetic actual = parser.parse(toPostfixString(a, b, "/"));
                    assertEquals(expected, actual);
                });
    }

}

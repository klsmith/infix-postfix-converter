package io.github.klsmith.ifpfc;

import static org.junit.Assert.assertEquals;
import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.doubles;
import static org.quicktheories.generators.SourceDSL.integers;

import org.junit.Test;

import io.github.klsmith.ifpfc.arithmetic.Add;
import io.github.klsmith.ifpfc.arithmetic.Arithmetic;
import io.github.klsmith.ifpfc.arithmetic.Divide;
import io.github.klsmith.ifpfc.arithmetic.Multiply;
import io.github.klsmith.ifpfc.arithmetic.Subtract;

public class PostfixArithmeticParserTest {

    private final ArithmeticParser parser = new PostfixArithmeticParser();

    private boolean assumeFiniteDoubles(double a, double b) {
        return Double.isFinite(a) && Double.isFinite(b);
    }

    private String withSpaces(Object... objects) {
        String[] strings = new String[objects.length];
        for (int i = 0; i < objects.length; i++) {
            strings[i] = objects[i].toString();
        }
        return String.join(" ", strings);
    }

    @Test
    public void testSimpleAddition() {
        qt().forAll(integers().all(), integers().all())
                .checkAssert((a, b) -> {
                    final Arithmetic expected = new Add(a, b);
                    final Arithmetic actual = parser.parse(withSpaces(a, b, "+"));
                    assertEquals(expected, actual);
                });
        qt().forAll(doubles().any(), doubles().any())
                .assuming(this::assumeFiniteDoubles)
                .checkAssert((a, b) -> {
                    final Arithmetic expected = new Add(a, b);
                    final Arithmetic actual = parser.parse(withSpaces(a, b, "+"));
                    assertEquals(expected, actual);
                });
    }

    @Test
    public void testSingleNestedAddition() {
        qt().forAll(integers().all(), integers().all(), integers().all())
                .checkAssert((a, b, c) -> {
                    final Arithmetic expected = new Add(new Add(a, b), c);
                    final Arithmetic actual = parser.parse(withSpaces(a, b, "+", c, "+"));
                    assertEquals(expected, actual);
                });
    }

    @Test
    public void testSimpleSubtraction() {
        qt().forAll(integers().all(), integers().all())
                .checkAssert((a, b) -> {
                    final Arithmetic expected = new Subtract(a, b);
                    final Arithmetic actual = parser.parse(withSpaces(a, b, "-"));
                    assertEquals(expected, actual);
                });
        qt().forAll(doubles().any(), doubles().any())
                .assuming(this::assumeFiniteDoubles)
                .checkAssert((a, b) -> {
                    final Arithmetic expected = new Subtract(a, b);
                    final Arithmetic actual = parser.parse(withSpaces(a, b, "-"));
                    assertEquals(expected, actual);
                });
    }

    @Test
    public void testSimpleMultiplication() {
        qt().forAll(integers().all(), integers().all())
                .checkAssert((a, b) -> {
                    final Arithmetic expected = new Multiply(a, b);
                    final Arithmetic actual = parser.parse(withSpaces(a, b, "*"));
                    assertEquals(expected, actual);
                });
        qt().forAll(doubles().any(), doubles().any())
                .assuming(this::assumeFiniteDoubles)
                .checkAssert((a, b) -> {
                    final Arithmetic expected = new Multiply(a, b);
                    final Arithmetic actual = parser.parse(withSpaces(a, b, "*"));
                    assertEquals(expected, actual);
                });
    }

    @Test
    public void testSimpleDivision() {
        qt().forAll(integers().all(), integers().all())
                .checkAssert((a, b) -> {
                    final Arithmetic expected = new Divide(a, b);
                    final Arithmetic actual = parser.parse(withSpaces(a, b, "/"));
                    assertEquals(expected, actual);
                });
        qt().forAll(doubles().any(), doubles().any())
                .assuming(this::assumeFiniteDoubles)
                .checkAssert((a, b) -> {
                    final Arithmetic expected = new Divide(a, b);
                    final Arithmetic actual = parser.parse(withSpaces(a, b, "/"));
                    assertEquals(expected, actual);
                });
    }

}

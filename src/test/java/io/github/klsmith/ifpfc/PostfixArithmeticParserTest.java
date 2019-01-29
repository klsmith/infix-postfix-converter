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

    private boolean assumeFiniteDoubles(Double... doubles) {
        for (Double doubleVal : doubles) {
            if (!Double.isFinite(doubleVal)) {
                return false;
            }
        }
        return true;
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
                    {
                        final Arithmetic expected = new Add(new Add(a, b), c);
                        final Arithmetic actual = parser.parse(withSpaces(a, b, "+", c, "+"));
                        assertEquals(expected, actual);
                    }
                    {
                        final Arithmetic expected = new Add(a, new Add(b, c));
                        final Arithmetic actual = parser.parse(withSpaces(a, b, c, "+", "+"));
                        assertEquals(expected, actual);
                    }
                });
        qt().forAll(doubles().any(), doubles().any(), doubles().any())
                .assuming(this::assumeFiniteDoubles)
                .checkAssert((a, b, c) -> {
                    {
                        final Arithmetic expected = new Add(new Add(a, b), c);
                        final Arithmetic actual = parser.parse(withSpaces(a, b, "+", c, "+"));
                        assertEquals(expected, actual);
                    }
                    {
                        final Arithmetic expected = new Add(a, new Add(b, c));
                        final Arithmetic actual = parser.parse(withSpaces(a, b, c, "+", "+"));
                        assertEquals(expected, actual);
                    }
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
    public void testSingleNestedSubtraction() {
        qt().forAll(integers().all(), integers().all(), integers().all())
                .checkAssert((a, b, c) -> {
                    {
                        final Arithmetic expected = new Subtract(new Subtract(a, b), c);
                        final Arithmetic actual = parser.parse(withSpaces(a, b, "-", c, "-"));
                        assertEquals(expected, actual);
                    }
                    {
                        final Arithmetic expected = new Subtract(a, new Subtract(b, c));
                        final Arithmetic actual = parser.parse(withSpaces(a, b, c, "-", "-"));
                        assertEquals(expected, actual);
                    }
                });
        qt().forAll(doubles().any(), doubles().any(), doubles().any())
                .assuming(this::assumeFiniteDoubles)
                .checkAssert((a, b, c) -> {
                    {
                        final Arithmetic expected = new Subtract(new Subtract(a, b), c);
                        final Arithmetic actual = parser.parse(withSpaces(a, b, "-", c, "-"));
                        assertEquals(expected, actual);
                    }
                    {
                        final Arithmetic expected = new Subtract(a, new Subtract(b, c));
                        final Arithmetic actual = parser.parse(withSpaces(a, b, c, "-", "-"));
                        assertEquals(expected, actual);
                    }
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
    public void testSingleNestedMultiplication() {
        qt().forAll(integers().all(), integers().all(), integers().all())
                .checkAssert((a, b, c) -> {
                    {
                        final Arithmetic expected = new Multiply(new Multiply(a, b), c);
                        final Arithmetic actual = parser.parse(withSpaces(a, b, "*", c, "*"));
                        assertEquals(expected, actual);
                    }
                    {
                        final Arithmetic expected = new Multiply(a, new Multiply(b, c));
                        final Arithmetic actual = parser.parse(withSpaces(a, b, c, "*", "*"));
                        assertEquals(expected, actual);
                    }
                });
        qt().forAll(doubles().any(), doubles().any(), doubles().any())
                .assuming(this::assumeFiniteDoubles)
                .checkAssert((a, b, c) -> {
                    {
                        final Arithmetic expected = new Multiply(new Multiply(a, b), c);
                        final Arithmetic actual = parser.parse(withSpaces(a, b, "*", c, "*"));
                        assertEquals(expected, actual);
                    }
                    {
                        final Arithmetic expected = new Multiply(a, new Multiply(b, c));
                        final Arithmetic actual = parser.parse(withSpaces(a, b, c, "*", "*"));
                        assertEquals(expected, actual);
                    }
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

    @Test
    public void testSingleNestedDivision() {
        qt().forAll(integers().all(), integers().all(), integers().all())
                .checkAssert((a, b, c) -> {
                    {
                        final Arithmetic expected = new Divide(new Divide(a, b), c);
                        final Arithmetic actual = parser.parse(withSpaces(a, b, "/", c, "/"));
                        assertEquals(expected, actual);
                    }
                    {
                        final Arithmetic expected = new Divide(a, new Divide(b, c));
                        final Arithmetic actual = parser.parse(withSpaces(a, b, c, "/", "/"));
                        assertEquals(expected, actual);
                    }
                });
        qt().forAll(doubles().any(), doubles().any(), doubles().any())
                .assuming(this::assumeFiniteDoubles)
                .checkAssert((a, b, c) -> {
                    {
                        final Arithmetic expected = new Divide(new Divide(a, b), c);
                        final Arithmetic actual = parser.parse(withSpaces(a, b, "/", c, "/"));
                        assertEquals(expected, actual);
                    }
                    {
                        final Arithmetic expected = new Divide(a, new Divide(b, c));
                        final Arithmetic actual = parser.parse(withSpaces(a, b, c, "/", "/"));
                        assertEquals(expected, actual);
                    }
                });
    }

}

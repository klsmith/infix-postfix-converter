package io.github.klsmith.ifpfc;

import static org.junit.Assert.assertEquals;
import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.doubles;
import static org.quicktheories.generators.SourceDSL.integers;

import java.util.function.BiFunction;

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

    public void testSimple(BiFunction<Integer, Integer, Arithmetic> intConstructor,
            BiFunction<Double, Double, Arithmetic> doubleConstructor, String operator) {
        qt().forAll(integers().all(), integers().all())
                .checkAssert((a, b) -> {
                    final Arithmetic expected = intConstructor.apply(a, b);
                    final Arithmetic actual = parser.parse(withSpaces(a, b, operator));
                    assertEquals(expected, actual);
                });
        qt().forAll(doubles().any(), doubles().any())
                .assuming(this::assumeFiniteDoubles)
                .checkAssert((a, b) -> {
                    final Arithmetic expected = doubleConstructor.apply(a, b);
                    final Arithmetic actual = parser.parse(withSpaces(a, b, operator));
                    assertEquals(expected, actual);
                });
    }

    @Test
    public void testSimpleAddition() {
        testSimple(Add::new, Add::new, "+");
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
        testSimple(Subtract::new, Subtract::new, "-");
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
        testSimple(Multiply::new, Multiply::new, "*");
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
        testSimple(Divide::new, Divide::new, "/");
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

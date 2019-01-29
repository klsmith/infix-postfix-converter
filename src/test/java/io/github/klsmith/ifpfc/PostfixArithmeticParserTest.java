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
import io.github.klsmith.ifpfc.arithmetic.Value;

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

    private void testSingleNested(BiFunction<Arithmetic, Arithmetic, Arithmetic> constructor, String operator) {
        qt().forAll(integers().all(), integers().all(), integers().all())
                .checkAssert((a, b, c) -> {
                    {
                        final Arithmetic expected = constructor.apply(
                                constructor.apply(new Value(a), new Value(b)),
                                new Value(c));
                        final Arithmetic actual = parser.parse(withSpaces(a, b, operator, c, operator));
                        assertEquals(expected, actual);
                    }
                    {
                        final Arithmetic expected = constructor.apply(
                                new Value(a),
                                constructor.apply(new Value(b), new Value(c)));
                        final Arithmetic actual = parser.parse(withSpaces(a, b, c, operator, operator));
                        assertEquals(expected, actual);
                    }
                });
        qt().forAll(doubles().any(), doubles().any(), doubles().any())
                .assuming(this::assumeFiniteDoubles)
                .checkAssert((a, b, c) -> {
                    {
                        final Arithmetic expected = constructor.apply(
                                constructor.apply(new Value(a), new Value(b)),
                                new Value(c));
                        final Arithmetic actual = parser.parse(withSpaces(a, b, operator, c, operator));
                        assertEquals(expected, actual);
                    }
                    {
                        final Arithmetic expected = constructor.apply(
                                new Value(a),
                                constructor.apply(new Value(b), new Value(c)));
                        final Arithmetic actual = parser.parse(withSpaces(a, b, c, operator, operator));
                        assertEquals(expected, actual);
                    }
                });
    }

    @Test
    public void testSimpleAddition() {
        testSimple(Add::new, Add::new, "+");
    }

    @Test
    public void testSingleNestedAddition() {
        testSingleNested(Add::new, "+");
    }

    @Test
    public void testSimpleSubtraction() {
        testSimple(Subtract::new, Subtract::new, "-");
    }

    @Test
    public void testSingleNestedSubtraction() {
        testSingleNested(Subtract::new, "-");
    }

    @Test
    public void testSimpleMultiplication() {
        testSimple(Multiply::new, Multiply::new, "*");
    }

    @Test
    public void testSingleNestedMultiplication() {
        testSingleNested(Multiply::new, "*");
    }

    @Test
    public void testSimpleDivision() {
        testSimple(Divide::new, Divide::new, "/");
    }

    @Test
    public void testSingleNestedDivision() {
        testSingleNested(Divide::new, "/");
    }

}

package io.github.klsmith.ifpfc;

import static org.junit.Assert.assertEquals;
import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.doubles;
import static org.quicktheories.generators.SourceDSL.integers;

import java.math.BigDecimal;
import java.util.function.BiFunction;

import org.junit.Test;

import io.github.klsmith.ifpfc.arithmetic.Add;
import io.github.klsmith.ifpfc.arithmetic.Arithmetic;
import io.github.klsmith.ifpfc.arithmetic.Value;

public class PostfixArithmeticStringWriterTest {

    private ArithmeticStringWriter writer = new PostfixArithmeticStringWriter();

    /* Utility Methods */

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
            Object obj = objects[i];
            if (obj instanceof Integer) {
                obj = BigDecimal.valueOf((Integer) obj);
            }
            if (obj instanceof Double) {
                obj = BigDecimal.valueOf((Double) obj);
            }
            strings[i] = obj.toString();
        }
        return String.join(" ", strings);
    }

    /* Abstract Testing Methods */

    public void testSimple(BiFunction<Arithmetic, Arithmetic, Arithmetic> constructor, String operator) {
        qt().forAll(integers().all(), integers().all())
                .checkAssert((a, b) -> {
                    final String expected = withSpaces(a, b, operator);
                    final String actual = writer.write(constructor.apply(
                            new Value(a),
                            new Value(b)));
                    assertEquals(expected, actual);
                });
        qt().forAll(doubles().any(), doubles().any())
                .assuming(this::assumeFiniteDoubles)
                .checkAssert((a, b) -> {
                    final String expected = withSpaces(a, b, operator);
                    final String actual = writer.write(constructor.apply(
                            new Value(a),
                            new Value(b)));
                    assertEquals(expected, actual);
                });
    }

    /* Addition Tests */

    @Test
    public void testSimpleAddition() {
        testSimple(Add::new, "+");
    }

}

package io.github.klsmith.ifpfc.arithmetic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.doubles;
import static org.quicktheories.generators.SourceDSL.integers;

import java.math.BigDecimal;
import java.util.Objects;

import org.junit.Test;

public class ValueTest {

    @Test
    public void testResolveEqualsGivenValue() {
        qt().forAll(integers().all())
                .checkAssert(integer -> {
                    final Value value = new Value(integer);
                    assertEquals(BigDecimal.valueOf(integer),
                            value.resolve());
                });
        qt().forAll(doubles().any())
                .assuming(Double::isFinite)
                .checkAssert(doubleVal -> {
                    final Value value = new Value(doubleVal);
                    assertEquals(BigDecimal.valueOf(doubleVal),
                            value.resolve());
                });
    }

    @Test
    public void testIdentityEquals() {
        qt().forAll(integers().all())
                .checkAssert(integer -> {
                    final Value value = new Value(integer);
                    assertTrue(value.equals(value));
                });
        qt().forAll(doubles().any())
                .assuming(Double::isFinite)
                .checkAssert(doubleValue -> {
                    final Value value = new Value(doubleValue);
                    assertTrue(value.equals(value));
                });
    }

    @Test
    public void testCopyEquals() {
        qt().forAll(integers().all())
                .checkAssert(integer -> {
                    final Value value = new Value(integer);
                    final Value copy = new Value(integer);
                    assertTrue(value.equals(copy));
                });
        qt().forAll(doubles().any())
                .assuming(Double::isFinite)
                .checkAssert(doubleVal -> {
                    final Value value = new Value(doubleVal);
                    final Value copy = new Value(doubleVal);
                    assertTrue(value.equals(copy));
                });
    }

    @Test
    public void testDifferentValuesNotEquals() {
        qt().forAll(integers().all(), integers().all())
                .assuming((a, b) -> !Objects.equals(a, b))
                .checkAssert((a, b) -> {
                    final Value valueA = new Value(a);
                    final Value valueB = new Value(b);
                    assertFalse(valueA.equals(valueB));
                });
        qt().forAll(doubles().any(), doubles().any())
                .assuming((a, b) -> !Objects.equals(a, b)
                        && Double.isFinite(a)
                        && Double.isFinite(b))
                .checkAssert((a, b) -> {
                    final Value valueA = new Value(a);
                    final Value valueB = new Value(b);
                    assertFalse(valueA.equals(valueB));
                });
    }

}

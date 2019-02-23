package io.github.klsmith.ifpfc.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.quicktheories.core.Gen;
import org.quicktheories.core.RandomnessSource;
import org.quicktheories.impl.Constraint;

public class ChooserGen<T> implements Gen<T> {

    private final List<T> choices;
    private final Constraint constraint;

    private ChooserGen(List<T> choices) {
        this.choices = new ArrayList<>(choices);
        this.constraint = Constraint.between(0, choices.size() - 1);
    }

    @SafeVarargs
    public static <T> ChooserGen<T> from(T... choices) {
        return new ChooserGen<>(Arrays.asList(choices));
    }

    @Override
    public T generate(RandomnessSource random) {
        final int index = (int) random.next(constraint);
        return choices.get(index);
    }

}

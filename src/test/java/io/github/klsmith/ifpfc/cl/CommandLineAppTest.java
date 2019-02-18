package io.github.klsmith.ifpfc.cl;

import static org.junit.Assert.assertEquals;
import static org.quicktheories.QuickTheory.qt;

import org.junit.Test;
import org.quicktheories.core.Gen;
import org.quicktheories.core.RandomnessSource;
import org.quicktheories.impl.Constraint;

import io.github.klsmith.ifpfc.cl.command.Command;
import io.github.klsmith.ifpfc.cl.command.ConvertCommand;
import io.github.klsmith.ifpfc.cl.command.ConvertCommand.Type;
import io.github.klsmith.ifpfc.cl.command.ExitCommand;
import io.github.klsmith.ifpfc.cl.command.HelpCommand;

public class CommandLineAppTest {

    private final CommandLineApp app = new CommandLineApp();

    @Test
    public void testExitCommand() {
        final Command expected = new ExitCommand(app);
        final Command actual = app.readCommand("exit");
        assertEquals(expected, actual);
    }

    @Test
    public void testHelpCommand() {
        final Command expected = new HelpCommand(System.out);
        final Command actual = app.readCommand("help");
        assertEquals(expected, actual);
    }

    @Test
    public void testConvertCommand() {
        qt().forAll(typeAny(), typeAny())
                .checkAssert((from, to) -> {
                    final Command expected = new ConvertCommand(System.out, from, to, "2 + 2");
                    final Command actual = app.readCommand(toConvertString(from, to, "2 + 2"));
                    assertEquals(expected, actual);
                });
    }

    private String toConvertString(Type from, Type to, String input) {
        return String.join(" ",
                "convert",
                from.toString().toLowerCase(),
                to.toString().toLowerCase(),
                input);
    }

    private Gen<Type> typeAny() {
        return new Gen<Type>() {

            @Override
            public Type generate(RandomnessSource random) {
                final Type[] types = Type.values();
                final int index = (int) random.next(Constraint.between(0, types.length - 1));
                return types[index];
            }

        };
    }

}

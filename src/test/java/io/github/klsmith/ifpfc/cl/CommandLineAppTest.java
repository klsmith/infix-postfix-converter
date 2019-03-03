package io.github.klsmith.ifpfc.cl;

import static org.junit.Assert.assertEquals;
import static org.quicktheories.QuickTheory.qt;

import org.junit.Test;
import org.quicktheories.core.Gen;

import io.github.klsmith.ifpfc.cl.command.Command;
import io.github.klsmith.ifpfc.cl.command.ConvertCommand;
import io.github.klsmith.ifpfc.cl.command.ConvertCommand.Type;
import io.github.klsmith.ifpfc.cl.command.ExitCommand;
import io.github.klsmith.ifpfc.cl.command.HelpCommand;
import io.github.klsmith.ifpfc.cl.command.ResolveCommand;
import io.github.klsmith.ifpfc.util.ChooserGen;
import io.github.klsmith.ifpfc.util.TestHelper;

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
        final Gen<Type> anyType = ChooserGen.from(Type.values());
        qt().forAll(anyType, anyType)
                .checkAssert((from, to) -> {
                    final Command expected = new ConvertCommand(System.out, from, to, "2 + 2");
                    final Command actual = app.readCommand(TestHelper.withSpaces("convert", from, to, "2 + 2"));
                    assertEquals(expected, actual);
                });
    }

    @Test
    public void testResolveCommand() {
        final Gen<Type> anyType = ChooserGen.from(Type.values());
        qt().forAll(anyType)
                .checkAssert(type -> {
                    final Command expected = new ResolveCommand(System.out, type, "2 + 2");
                    final Command actual = app.readCommand(TestHelper.withSpaces("resolve", type, "2 + 2"));
                    assertEquals(expected, actual);
                });
    }

}

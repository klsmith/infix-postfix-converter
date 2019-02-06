package io.github.klsmith.ifpfc.cl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CommandTest {

    @Test
    public void testIdentityEquals() {
        final Command command = Command.builder()
                .setCommandType(Command.Type.PARSE)
                .setNotation(Command.Notation.INFIX)
                .setInput("2 + 2")
                .build();
        assertTrue(command.equals(command));
    }

    @Test
    public void testCopyEquals() {
        final Command.Builder builder = Command.builder()
                .setCommandType(Command.Type.PARSE)
                .setNotation(Command.Notation.INFIX)
                .setInput("2 + 2");
        assertTrue(builder.build().equals(builder.build()));
    }

    @Test
    public void testSimpleParseInfix() {
        final Command expected = Command.builder()
                .setCommandType(Command.Type.PARSE)
                .setNotation(Command.Notation.INFIX)
                .setInput("2 + 2")
                .build();
        final Command actual = Command.fromString("parse infix 2 + 2");
        assertEquals(expected, actual);
    }

}

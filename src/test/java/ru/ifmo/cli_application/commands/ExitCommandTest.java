package ru.ifmo.cli_application.commands;

import org.junit.Test;
import ru.ifmo.cli_application.Context;

import static org.junit.Assert.assertTrue;

public class ExitCommandTest {
    @Test
    public void testExit() {
        Context ctx = new Context();
        ExitCommand exitCommand = new ExitCommand(ctx);
        exitCommand.execute(null, null);
        assertTrue(ctx.isClosed());
    }
}

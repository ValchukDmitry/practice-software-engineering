package ru.ifmo.cli_application.commands;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class WcCommandTest {
    @Test
    public void testWcWithoutArgs() {
        WcCommand command = new WcCommand();
        String result = command.execute(new ArrayList<>(), "one two three");
        assertEquals("1\t3\t13", result);
    }
}

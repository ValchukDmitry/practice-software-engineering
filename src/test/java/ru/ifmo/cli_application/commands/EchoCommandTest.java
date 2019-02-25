package ru.ifmo.cli_application.commands;

import org.junit.Test;
import ru.ifmo.cli_application.tokens.FullyQuoted;
import ru.ifmo.cli_application.tokens.IToken;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EchoCommandTest {
    @Test
    public void testEcho() {
        EchoCommand echoCommand = new EchoCommand();
        List<IToken> arguments = Arrays.asList(new FullyQuoted[]{new FullyQuoted("\"hello\"")});
        String result = echoCommand.execute(arguments, "");
        assertEquals("hello ", result);
    }
}

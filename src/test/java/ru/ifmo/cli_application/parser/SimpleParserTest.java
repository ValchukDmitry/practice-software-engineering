package ru.ifmo.cli_application.parser;

import org.junit.Test;
import ru.ifmo.cli_application.Context;
import ru.ifmo.cli_application.commands.CatCommand;
import ru.ifmo.cli_application.commands.EchoCommand;
import ru.ifmo.cli_application.tokens.*;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SimpleParserTest {
    @Test
    public void testParsingOneInnerCommand() {
        Context ctx = new Context();
        Parser parser = new SimpleParser(ctx);
        List<IToken> tokens = parser.parseTokens("echo");
        assertEquals(1, tokens.size());
        assertTrue(tokens.get(0) instanceof EchoCommand);
    }

    @Test
    public void testParsingInnerCommandWithArgs() {
        Context ctx = new Context();
        Parser parser = new SimpleParser(ctx);
        List<IToken> tokens = parser.parseTokens("echo 123");
        assertEquals(2, tokens.size());
        assertTrue(tokens.get(0) instanceof EchoCommand);
        assertTrue(tokens.get(1) instanceof Argument);
    }

    @Test
    public void testParsingCommandsWithPipe() {
        Context ctx = new Context();
        Parser parser = new SimpleParser(ctx);
        List<IToken> tokens = parser.parseTokens("echo 123 | cat");
        assertEquals(4, tokens.size());
        assertTrue(tokens.get(0) instanceof EchoCommand);
        assertTrue(tokens.get(1) instanceof Argument);
        assertTrue(tokens.get(2) instanceof Pipe);
        assertTrue(tokens.get(3) instanceof CatCommand);
    }


    @Test
    public void testParsingCommandWithQuotedArg() {
        Context ctx = new Context();
        Parser parser = new SimpleParser(ctx);
        List<IToken> tokens = parser.parseTokens("echo \"123 | cat\"");
        assertEquals(2, tokens.size());
        assertTrue(tokens.get(0) instanceof EchoCommand);
        assertTrue(tokens.get(1) instanceof Quoted);
    }

    @Test
    public void testParsingCommandWithFullyQuotedArg() {
        Context ctx = new Context();
        Parser parser = new SimpleParser(ctx);
        List<IToken> tokens = parser.parseTokens("echo \'123 | cat\'");
        assertEquals(2, tokens.size());
        assertTrue(tokens.get(0) instanceof EchoCommand);
        assertTrue(tokens.get(1) instanceof FullyQuoted);
    }

    @Test
    public void testParsingCommandWithFullyQuotedArgContainsQuote() {
        Context ctx = new Context();
        Parser parser = new SimpleParser(ctx);
        List<IToken> tokens = parser.parseTokens("echo \'1\" | 2\'");
        assertEquals(2, tokens.size());
        assertTrue(tokens.get(0) instanceof EchoCommand);
        assertTrue(tokens.get(1) instanceof FullyQuoted);
    }

    @Test
    public void testParsingCommandWithFullyQuotedArgWithDollar() {
        Context ctx = new Context();
        Parser parser = new SimpleParser(ctx);
        List<IToken> tokens = parser.parseTokens("echo \'$x\'");
        assertEquals(2, tokens.size());
        assertTrue(tokens.get(0) instanceof EchoCommand);
        assertTrue(tokens.get(1) instanceof FullyQuoted);
    }
}

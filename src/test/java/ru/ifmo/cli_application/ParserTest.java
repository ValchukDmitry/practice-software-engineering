package ru.ifmo.cli_application;

import org.junit.Test;
import ru.ifmo.cli_application.commands.Command;
import ru.ifmo.cli_application.tokens.AssigningOperator;
import ru.ifmo.cli_application.tokens.IToken;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ParserTest {
    @Test
    public void testParsing() {
        Parser parser = new Parser(s -> Arrays.asList(s.split(" ")));
        parser.addParsingRule(s -> true, s -> new Command(s));
        assertEquals(5, parser.parseTokens("first second third forth fifth").size());
    }

    @Test
    public void testRulesApplied() {
        Parser parser = new Parser(s -> Arrays.asList(s.split(" ")));
        Context ctx = new Context();
        parser.addParsingRule(s -> s.contains("="), s -> new AssigningOperator(s, ctx));
        parser.addParsingRule(s -> true, s -> new Command(s));
        List<IToken> result = parser.parseTokens("hello world=5 one=two");
        assertTrue(result.get(0) instanceof Command);
        assertTrue(result.get(1) instanceof AssigningOperator);
        assertTrue(result.get(2) instanceof AssigningOperator);
    }
}

package ru.ifmo.cli_application.commands;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import ru.ifmo.cli_application.tokens.IToken;
import ru.ifmo.cli_application.tokens.SimpleToken;

import static org.junit.Assert.assertEquals;

public class GrepCommandTest {
    @Test
    public void testExecutionWithoutFlagsWithSubstring() {
        GrepCommand command = new GrepCommand();
        List<IToken> tokens = new ArrayList<>();
        tokens.add(new SimpleToken("hel"));
        assertEquals("hello", command.execute(tokens, "hello"));
    }

    @Test
    public void testExecutionWithoutFlagsWithNotSubsting() {
        GrepCommand command = new GrepCommand();
        List<IToken> tokens = new ArrayList<>();
        tokens.add(new SimpleToken("good"));
        assertEquals("", command.execute(tokens, "hello"));
    }

    @Test
    public void testExecutionWordFlagWithOneMatch() {
        GrepCommand command = new GrepCommand();
        List<IToken> tokens = new ArrayList<>();
        tokens.add(new SimpleToken("-w"));
        tokens.add(new SimpleToken("hello"));
        assertEquals("hello", command.execute(tokens, "hellolo\nhello"));
    }

    @Test
    public void testExecutionWordFlagWithManyMatches() {
        GrepCommand command = new GrepCommand();
        List<IToken> tokens = new ArrayList<>();
        tokens.add(new SimpleToken("-w"));
        tokens.add(new SimpleToken("hello"));
        assertEquals("hello\nhello", command.execute(tokens, "hellolo\nhello\nhello"));
    }

    @Test
    public void testExecutionIgnoreCaseFlagWithIgnoring() {
        GrepCommand command = new GrepCommand();
        List<IToken> tokens = new ArrayList<>();
        tokens.add(new SimpleToken("-i"));
        tokens.add(new SimpleToken("hello"));
        assertEquals("HellO", command.execute(tokens, "HellO"));
    }

    @Test
    public void testExecutionIgnoreCaseFlagWithoutIgnoreNeeded() {
        GrepCommand command = new GrepCommand();
        List<IToken> tokens = new ArrayList<>();
        tokens.add(new SimpleToken("-i"));
        tokens.add(new SimpleToken("hello"));
        assertEquals("hello", command.execute(tokens, "hello"));
    }

    @Test
    public void testExecutionAfterFlagWithEnoughLines() {
        GrepCommand command = new GrepCommand();
        List<IToken> tokens = new ArrayList<>();
        tokens.add(new SimpleToken("-A"));
        tokens.add(new SimpleToken("3"));
        tokens.add(new SimpleToken("hello"));
        assertEquals("hello\nworld\nmy\nname", command.execute(tokens, "hello\nworld\nmy\nname\nis"));
    }

    @Test
    public void testExecutionAfterFlagWithNotEnoughStrings() {
        GrepCommand command = new GrepCommand();
        List<IToken> tokens = new ArrayList<>();
        tokens.add(new SimpleToken("-A"));
        tokens.add(new SimpleToken("3"));
        tokens.add(new SimpleToken("hello"));
        assertEquals("hello\nworld", command.execute(tokens, "hello\nworld"));
    }

    @Test
    public void testAfterWithMatchesInARow() {
        GrepCommand command = new GrepCommand();
        List<IToken> tokens = new ArrayList<>();
        tokens.add(new SimpleToken("-A"));
        tokens.add(new SimpleToken("2"));
        tokens.add(new SimpleToken("apply"));
        String expected = "apply plugin: 'java'\n" +
                "apply plugin: 'application'\n" +
                "\n" +
                "sourceCompatibility = 1.9";
        String text = "version '1.0-SNAPSHOT'\n" +
                "\n" +
                "apply plugin: 'java'\n" +
                "apply plugin: 'application'\n" +
                "\n" +
                "sourceCompatibility = 1.9\n" +
                "\n" +
                "repositories {\n";
        assertEquals(expected, command.execute(tokens, text));
    }
}

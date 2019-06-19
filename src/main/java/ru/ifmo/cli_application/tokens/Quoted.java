package ru.ifmo.cli_application.tokens;

import java.util.List;

import ru.ifmo.cli_application.parser.Parser;

public class Quoted implements IToken {
    private String string;
    private Parser parser;

    public Quoted(Parser parser, String string) {
        this.parser = parser;
        this.string = string.substring(1, string.length() - 1);
    }

    @Override
    public String getValue() {
        List<IToken> tokens = parser.parseTokens(this.string);
        StringBuilder result = new StringBuilder();
        for (IToken token : tokens) {
            result.append(token.getValue());
            result.append(" ");
        }
        return result.toString();
    }
}

package ru.ifmo.cli_application.tokens;

public class SimpleToken implements IToken {
    private String tokenString;
    public SimpleToken(String arg) {
        tokenString = arg;
    }

    @Override
    public String getValue() {
        return tokenString;
    }
}

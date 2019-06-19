package ru.ifmo.cli_application.tokens;

public class Pipe implements IToken, IDelimiter {
    @Override
    public String getValue() {
        return "|";
    }
}

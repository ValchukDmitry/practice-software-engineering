package ru.ifmo.cli_application.tokens;

public class FullyQuoted implements IToken {
    private String string;

    public FullyQuoted(String string) {
        this.string = string.substring(1, string.length() - 1);
    }

    public String toString() {
        return string;
    }
}

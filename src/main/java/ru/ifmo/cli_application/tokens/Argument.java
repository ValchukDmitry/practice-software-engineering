package ru.ifmo.cli_application.tokens;

public class Argument implements IToken {
    private String value;

    public Argument(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}

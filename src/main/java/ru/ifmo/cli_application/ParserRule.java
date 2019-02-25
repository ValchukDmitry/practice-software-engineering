package ru.ifmo.cli_application;

import ru.ifmo.cli_application.tokens.IToken;

import java.util.function.Function;

public class ParserRule {
    private Function<String, Boolean> predicate;
    private Function<String, IToken> constructor;

    public ParserRule(Function<String, Boolean> predicate, Function<String, IToken> constructor) {
        this.predicate = predicate;
        this.constructor = constructor;
    }

    public Function<String, Boolean> getPredicate() {
        return predicate;
    }

    public Function<String, IToken> getConstructor() {
        return constructor;
    }
}

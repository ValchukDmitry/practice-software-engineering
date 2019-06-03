package ru.ifmo.cli_application.parser;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import ru.ifmo.cli_application.tokens.IToken;

/**
 * Rule for parsing string tokens to IToken
 */
public class ParserRule {
    private BiFunction<String, List<IToken>, Boolean> predicate;
    private Function<String, IToken> constructor;

    public ParserRule(BiFunction<String, List<IToken>, Boolean> predicate, Function<String, IToken> constructor) {
        this.predicate = predicate;
        this.constructor = constructor;
    }

    public BiFunction<String, List<IToken>, Boolean> getPredicate() {
        return predicate;
    }

    public Function<String, IToken> getConstructor() {
        return constructor;
    }
}

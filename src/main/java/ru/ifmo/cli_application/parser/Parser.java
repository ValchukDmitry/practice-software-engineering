package ru.ifmo.cli_application.parser;

import ru.ifmo.cli_application.tokens.IToken;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Parser {
    private List<ParserRule> rules;
    private Function<String, List<String>> splittingFunction;

    public Parser(Function<String, List<String>> splittingFunction) {
        this.rules = new ArrayList<>();
        this.splittingFunction = splittingFunction;
    }

    public void addParsingRule(ParserRule parserRule) {
        this.rules.add(parserRule);
    }

    public List<IToken> parseTokens(String command) {
        List<String> splitedCommand = splittingFunction.apply(command);
        List<IToken> outputTokens = new ArrayList<>(splitedCommand.size());
        for (String commandToken : splitedCommand) {
            if (commandToken.isEmpty()) {
                continue;
            }
            for (ParserRule rule : rules) {
                if (rule.getPredicate().apply(commandToken, outputTokens)) {
                    outputTokens.add(rule.getConstructor().apply(commandToken));
                    break;
                }
            }
        }
        return outputTokens;
    }
}

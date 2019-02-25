package ru.ifmo.cli_application;

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

    public void addParsingRule(Function<String, Boolean> predicate, Function<String, IToken> constructor) {
        this.rules.add(new ParserRule(predicate, constructor));
    }

    public List<IToken> parseTokens(String command) {
        List<String> splitedCommand = splittingFunction.apply(command);
        List<IToken> outputTokens = new ArrayList<>(splitedCommand.size());
        for (String commandToken : splitedCommand) {
            if (commandToken.isEmpty() || commandToken.equals("\n")) {
                continue;
            }
            for (ParserRule rule : rules) {
                if (rule.getPredicate().apply(commandToken)) {
                    outputTokens.add(rule.getConstructor().apply(commandToken));
                    break;
                }
            }
        }
        return outputTokens;
    }
}

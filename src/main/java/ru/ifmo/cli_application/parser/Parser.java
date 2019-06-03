package ru.ifmo.cli_application.parser;

import java.util.ArrayList;
import java.util.List;

import ru.ifmo.cli_application.tokens.IToken;

/**
 *  Parses string to {@link List<IToken>} using {@link ParserRule}
 */
public abstract class Parser {
    private List<ParserRule> rules;

    public Parser() {
        this.rules = new ArrayList<>();
    }

    /**
     * Method to add new parsing rule
     * @param parserRule
     */
    public void addParsingRule(ParserRule parserRule) {
        this.rules.add(parserRule);
    }

    /**
     * Function that split command string to list of string tokens and then, parse each token using {@link ParserRule}
     * @param command
     * @return List of parsed tokens
     */
    public List<IToken> parseTokens(String command) {
        List<String> splitedCommand = commandPreprocessing(command);
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

    /**
     * Function for split and preprocess command
     * @param command
     * @return {@link List<String>} of preprocessed command
     */
    public abstract List<String> commandPreprocessing(String command);
}

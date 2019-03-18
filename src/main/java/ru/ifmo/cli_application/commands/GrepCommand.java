package ru.ifmo.cli_application.commands;

import net.sourceforge.argparse4j.impl.Arguments;
import ru.ifmo.cli_application.tokens.IExecutable;
import ru.ifmo.cli_application.tokens.IToken;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GrepCommand implements IToken, IExecutable {

    private String splitWordsRegex = "^|[ |\\n|\\r|\\t]+";

    private Namespace parseArguments(List<IToken> tokens) throws ArgumentParserException {
        String[] args = tokens.stream().map(IToken::getValue).toArray(String[]::new);
        ArgumentParser parser = ArgumentParsers.newFor("Grep").build();
        parser.addArgument("-w").dest("whole word").action(Arguments.storeTrue());
        parser.addArgument("-i").dest("ignore case").action(Arguments.storeTrue());
        parser.addArgument("-A").dest("after").setDefault(0).type(Integer.class);
        parser.addArgument("regex");
        parser.addArgument("file").nargs("*");
        return parser.parseArgs(args);
    }

    @Override
    public String execute(List<IToken> args, String inputStream) {
        Namespace namespace = null;
        try {
            namespace = parseArguments(args);
        } catch (ArgumentParserException e) {
            return e.toString();
        }
        String regex = namespace.getString("regex");
        String inputStreamForMatcher = inputStream;
        String[] lines = inputStream.split("\n");
        if (namespace.get("ignore case")) {
            regex = regex.toLowerCase();
        }


        Pattern pattern = Pattern.compile(regex);
        StringBuilder stringBuilder = new StringBuilder();

        int printAfter = 0;
        for (String line : lines) {
            if (printAfter > 0) {
                printAfter--;
                stringBuilder.append(line).append('\n');
                continue;
            }
            String lineMatcher = line;
            if (namespace.get("ignore case")) {
                lineMatcher = lineMatcher.toLowerCase();
            }
            if (namespace.get("whole word")) {
                String[] words = line.split(splitWordsRegex);
                long count = Arrays.stream(words).filter(word -> pattern.matcher(word).matches()).count();
                if (count > 0) {
                    stringBuilder.append(line).append('\n');
                    printAfter = namespace.get("after");
                }
            } else {
                Matcher matcher = pattern.matcher(lineMatcher);
                if (matcher.find()) {
                    stringBuilder.append(line).append('\n');
                    printAfter = namespace.get("after");
                }
            }
        }

        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("\n"));
        }
        return stringBuilder.toString();
    }

    @Override
    public String getValue() {
        return "grep";
    }
}

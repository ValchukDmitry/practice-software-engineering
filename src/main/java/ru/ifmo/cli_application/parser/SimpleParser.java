package ru.ifmo.cli_application.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import ru.ifmo.cli_application.Context;
import ru.ifmo.cli_application.commands.*;
import ru.ifmo.cli_application.tokens.*;

/**
 * Parser implementation for our CLI application
 */
public class SimpleParser extends Parser {
    private static Pattern splittingPattern = Pattern.compile("[^\\s\"']+|\"[^\"]*\"|'[^']*'");
    private Context ctx;

    public SimpleParser(Context ctx) {
        this.ctx = ctx;
        addParsingRules();
    }

    private void addParsingRules() {
        List<ParserRule> predicates = new ArrayList<>();
        Function<List<IToken>, Boolean> isCommand = (List<IToken> t) -> t.size() == 0 || t.get(t.size() - 1) instanceof IDelimiter;
        predicates.add(new ParserRule((String s, List<IToken> t) -> s.startsWith("\'") && s.endsWith("\'"), FullyQuoted::new));
        predicates.add(new ParserRule((String s, List<IToken> t) -> s.startsWith("\"") && s.endsWith("\""), (String s) -> new Quoted(this, s)));
        predicates.add(new ParserRule((String s, List<IToken> t) -> s.startsWith("$"), (String s) -> new Variable(s, ctx)));
        predicates.add(new ParserRule((String s, List<IToken> t) -> s.equals("|"), (String s) -> new Pipe()));
        predicates.add(new ParserRule((String s, List<IToken> t) -> s.contains("="), (String s) -> new AssigningOperator(s, ctx)));
        predicates.add(new ParserRule((String s, List<IToken> t) -> isCommand.apply(t) && s.equals("cat"), (String s) -> new CatCommand()));
        predicates.add(new ParserRule((String s, List<IToken> t) -> isCommand.apply(t) && s.equals("echo"), (String s) -> new EchoCommand()));
        predicates.add(new ParserRule((String s, List<IToken> t) -> isCommand.apply(t) && s.equals("pwd"), (String s) -> new PwdCommand()));
        predicates.add(new ParserRule((String s, List<IToken> t) -> isCommand.apply(t) && s.equals("wc"), (String s) -> new WcCommand()));
        predicates.add(new ParserRule((String s, List<IToken> t) -> isCommand.apply(t) && s.equals("exit"), (String s) -> new ExitCommand(ctx)));
        predicates.add(new ParserRule((String s, List<IToken> t) -> isCommand.apply(t), Command::new));
        predicates.add(new ParserRule((String s, List<IToken> t) -> true, Argument::new));
        for (ParserRule p : predicates) {
            super.addParsingRule(p);
        }
    }

    public List<String> commandPreprocessing(String command) {
        Matcher matcher = splittingPattern.matcher(command);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        List<String> finalList = new ArrayList<>();
        for (String elem : list) {
            if (elem.contains("$") && !elem.startsWith("\'") && !elem.endsWith("\'")) {
                String[] splitedElem = elem.split("\\$");
                if (elem.startsWith("$")) {
                    splitedElem[0] = new Variable("$" + splitedElem[0], ctx).getValue();
                }
                for (int i = 1; i < splitedElem.length; i++) {
                    splitedElem[i] = new Variable("$" + splitedElem[i], ctx).getValue();
                }
                finalList.add(Arrays.stream(splitedElem).collect(Collectors.joining("")));
            } else {
                finalList.add(elem);
            }
        }
        return finalList;
    }
}

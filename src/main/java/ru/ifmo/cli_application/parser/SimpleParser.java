package ru.ifmo.cli_application.parser;

import ru.ifmo.cli_application.Context;
import ru.ifmo.cli_application.commands.*;
import ru.ifmo.cli_application.tokens.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleParser extends Parser {
    private static Pattern splittingPattern = Pattern.compile("[^\\s\"']+|\"[^\"]*\"|'[^']*'");
    private Context ctx;

    public SimpleParser(Context ctx) {
        super(SimpleParser::splitFunction);
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

    private static List<String> splitFunction(String string) {
        Matcher matcher = splittingPattern.matcher(string);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }
}

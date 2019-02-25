package ru.ifmo.cli_application;

import javafx.util.Pair;
import ru.ifmo.cli_application.commands.*;
import ru.ifmo.cli_application.tokens.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {
    private Context ctx;
    private Parser parser;
    private Executor executor;
    private InputStream inputStream;
    private OutputStream outputStream;

    public Application(InputStream inputStream, OutputStream outputStream) {
        ctx = new Context();

        Pattern splittingPattern = Pattern.compile("([^\"]\\S*|\".+?\")\\s*");

        parser = new Parser((s) -> {
            Matcher matcher = splittingPattern.matcher(s);
            List<String> list = new ArrayList<String>();
            while (matcher.find()) {
                list.add(matcher.group(1));
            }
            return list;
        });
        this.inputStream = inputStream;
        this.outputStream = outputStream;

        List<Pair<Function<String, Boolean>, Function<String, IToken>>> predicates = new ArrayList<>();
        predicates.add(new Pair<>((String s) -> s.startsWith("\'") && s.endsWith("\'"), FullyQuoted::new));
        predicates.add(new Pair<>((String s) -> s.startsWith("\"") && s.endsWith("\""), (String s) -> new Quoted(parser, s)));
        predicates.add(new Pair<>((String s) -> s.startsWith("$"), (String s) -> new Variable(s, ctx)));
        predicates.add(new Pair<>((String s) -> s.equals("|"), (String s) -> new Pipe()));
        predicates.add(new Pair<>((String s) -> s.contains("="), (String s) -> new AssigningOperator(s, ctx)));
        predicates.add(new Pair<>((String s) -> s.equals("cat"), (String s) -> new CatCommand()));
        predicates.add(new Pair<>((String s) -> s.equals("echo"), (String s) -> new EchoCommand()));
        predicates.add(new Pair<>((String s) -> s.equals("pwd"), (String s) -> new PwdCommand()));
        predicates.add(new Pair<>((String s) -> s.equals("wc"), (String s) -> new WcCommand()));
        predicates.add(new Pair<>((String s) -> s.equals("exit"), (String s) -> new ExitCommand(ctx)));
        predicates.add(new Pair<>((String s) -> true, Command::new));
        for (Pair<Function<String, Boolean>, Function<String, IToken>> p : predicates) {
            parser.addParsingRule(p.getKey(), p.getValue());
        }
        executor = new Executor(ctx);
    }

    public void start() throws IOException {
        Scanner scanner = new Scanner(inputStream);

        while (!ctx.isClosed()) {
            List<IToken> tokens = parser.parseTokens(scanner.nextLine());
            String result = executor.execute(tokens);
            outputStream.write(result.getBytes());
            outputStream.flush();
        }
    }
}

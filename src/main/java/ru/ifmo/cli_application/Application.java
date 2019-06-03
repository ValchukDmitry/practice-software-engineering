package ru.ifmo.cli_application;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Scanner;

import ru.ifmo.cli_application.parser.Parser;
import ru.ifmo.cli_application.parser.SimpleParser;
import ru.ifmo.cli_application.tokens.IToken;

/**
 * Main CLI application class
 */
public class Application {
    private Context ctx;
    private Parser parser;
    private Executor executor;
    private InputStream inputStream;
    private OutputStream outputStream;

    /**
     * CLI application constructor
     * @param inputStream stream for commands input
     * @param outputStream stream for commands result output
     */
    public Application(InputStream inputStream, OutputStream outputStream) {
        ctx = new Context();
        parser = new SimpleParser(ctx);
        this.inputStream = inputStream;
        this.outputStream = outputStream;

        executor = new Executor(ctx);
    }

    /**
     * Method for starting cli application
     * @throws IOException in case of input or output stream is not working
     */
    public void start() throws IOException {
        Scanner scanner = new Scanner(inputStream);

        while (!ctx.isClosed()) {
            try {
                List<IToken> tokens = parser.parseTokens(scanner.nextLine());
                String result = executor.execute(tokens);
                outputStream.write(result.getBytes());
                outputStream.flush();
            } catch (Throwable e) {
                outputStream.write(e.getMessage().getBytes());
                outputStream.flush();
            }
        }
    }
}

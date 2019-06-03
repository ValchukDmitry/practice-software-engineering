package ru.ifmo.cli_application.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import ru.ifmo.cli_application.tokens.IExecutable;
import ru.ifmo.cli_application.tokens.IToken;

/**
 * Class for concatenation files command
 */
public class CatCommand implements IToken, IExecutable {
    @Override
    public String execute(List<IToken> args, String inputStream) {
        if (args.size() == 0) {
            return inputStream;
        }
        StringBuilder resultString = new StringBuilder();
        for (IToken fileName : args) {
            try (Stream<String> lines = Files.lines(Paths.get(fileName.getValue()))) {
                lines.forEach((s)->resultString.append(s).append('\n'));
            } catch (IOException e) {
                return "File not found: " + fileName.getValue();
            }
        }
        return resultString.toString();
    }

    @Override
    public String getValue() {
        return "cat";
    }
}

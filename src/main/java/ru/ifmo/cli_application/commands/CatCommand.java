package ru.ifmo.cli_application.commands;

import ru.ifmo.cli_application.tokens.IExecutable;
import ru.ifmo.cli_application.tokens.IToken;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

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
            try {
                Scanner scanner = new Scanner(new FileInputStream(fileName.toString()));
                while (scanner.hasNextLine()) {
                    resultString.append(scanner.nextLine());
                }
            } catch (IOException e) {
                return "File not found: " + fileName.toString();
            }
        }
        return resultString.toString();
    }
}

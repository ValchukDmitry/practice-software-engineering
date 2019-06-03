package ru.ifmo.cli_application.commands;

import java.util.List;

import ru.ifmo.cli_application.tokens.IExecutable;
import ru.ifmo.cli_application.tokens.IToken;

/**
 * Class for echo command
 */
public class EchoCommand implements IToken, IExecutable {
    @Override
    public String execute(List<IToken> args, String inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        for (IToken arg : args) {
            stringBuilder.append(arg.getValue());
            stringBuilder.append(' ');
        }
        return stringBuilder.toString();
    }

    @Override
    public String getValue() {
        return "echo";
    }
}

package ru.ifmo.cli_application.commands;

import ru.ifmo.cli_application.tokens.IExecutable;
import ru.ifmo.cli_application.tokens.IToken;

import java.util.List;

/**
 * Class for showing current working directory command
 */
public class PwdCommand implements IToken, IExecutable {
    @Override
    public String execute(List<IToken> args, String inputStream) {
        return System.getProperty("user.dir");
    }

    @Override
    public String getValue() {
        return "pwd";
    }
}

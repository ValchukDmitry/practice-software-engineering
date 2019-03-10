package ru.ifmo.cli_application.commands;

import ru.ifmo.cli_application.tokens.IExecutable;
import ru.ifmo.cli_application.tokens.IToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for execution external bash commands
 */
public class Command implements IToken, IExecutable {
    private String commandName;

    public Command(String commandName) {
        this.commandName = commandName;
    }

    @Override
    public String execute(List<IToken> args, String inputStream) {
        try {
            List<String> result = new ArrayList<>();
            result.add(commandName);
            for (IToken arg : args) {
                result.add(arg.getValue());
            }
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(result);
            Process process = processBuilder.start();
            InputStream processOutput = process.getInputStream();
            return new String(processOutput.readAllBytes());
        } catch (IOException e) {
            return commandName + ": command not found";
        }
    }

    public String getValue() {
        return commandName;
    }
}

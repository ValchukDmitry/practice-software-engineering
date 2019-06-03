package ru.ifmo.cli_application.tokens;

import java.util.List;

/**
 * Interface of executable commands
 */
public interface IExecutable {
    /**
     * Function to execute command
     *
     * @param args        command arguments
     * @param inputStream result of previous commands execution in pipe
     * @return result of command execution
     */
    String execute(List<IToken> args, String inputStream);
}

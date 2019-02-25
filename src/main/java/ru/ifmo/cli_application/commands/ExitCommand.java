package ru.ifmo.cli_application.commands;

import ru.ifmo.cli_application.Context;
import ru.ifmo.cli_application.tokens.IExecutable;
import ru.ifmo.cli_application.tokens.IToken;

import java.util.List;

/**
 * Class for exit CLI application command
 */
public class ExitCommand implements IToken, IExecutable {
    private Context ctx;

    public ExitCommand(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public String execute(List<IToken> args, String inputStream) {
        ctx.exit();
        return "";
    }
}

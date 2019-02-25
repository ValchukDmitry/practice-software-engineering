package ru.ifmo.cli_application;

import ru.ifmo.cli_application.tokens.IDelimiter;
import ru.ifmo.cli_application.tokens.IExecutable;
import ru.ifmo.cli_application.tokens.IToken;

import java.util.ArrayList;
import java.util.List;

public class Executor {
    private Context ctx;

    public Executor(Context ctx) {
        this.ctx = ctx;
    }

    public String execute(List<IToken> tokens) {
        String prevOutput = "";
        if (tokens.isEmpty()) {
            return "";
        }
        List<IToken> tokensToExecute = new ArrayList<>();
        IExecutable executableCommand = null;
        for (IToken token : tokens) {
            if (executableCommand == null) {
                executableCommand = ((IExecutable) token);
                continue;
            }
            if (token instanceof IDelimiter) {
                prevOutput = executableCommand.execute(tokensToExecute, prevOutput);
                executableCommand = null;
                tokensToExecute.clear();
                continue;
            }
            tokensToExecute.add(token);
        }
        if (executableCommand != null) {
            prevOutput = executableCommand.execute(tokensToExecute, prevOutput);
        }
        prevOutput += "\n";
        return prevOutput;
    }
}

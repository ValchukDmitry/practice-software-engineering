package ru.ifmo.cli_application.tokens;

import ru.ifmo.cli_application.Context;

import java.util.List;

/**
 * Class for assigning operator execution
 */
public class AssigningOperator implements IToken, IExecutable {
    private String rawString;
    private Context ctx;

    public AssigningOperator(String s, Context ctx) {
        this.rawString = s;
        if (!this.rawString.contains("=")) {
            throw new IllegalArgumentException("first argument should contains '='");
        }
        if (ctx == null) {
            throw new NullPointerException("context should be initialized");
        }
        this.ctx = ctx;
    }


    @Override
    public String execute(List<IToken> args, String inputStream) {
        String[] splitedString = rawString.split("=");
        ctx.setVariable(splitedString[0], splitedString[1]);
        return "";
    }

    @Override
    public String getValue() {
        return "=";
    }
}

package ru.ifmo.cli_application.tokens;

import ru.ifmo.cli_application.Context;

public class Variable implements IToken {
    String variable;
    Context ctx;

    public Variable(String var, Context ctx) {
        this.variable = var;
        this.ctx = ctx;
    }

    public String getValue() {
        return ctx.getVariable(variable.substring(1));
    }
}

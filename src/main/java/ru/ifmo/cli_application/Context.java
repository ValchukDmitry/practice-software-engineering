package ru.ifmo.cli_application;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private Map<String, String> variables;
    private boolean isClosed = false;

    public Context() {
        this.variables = new HashMap<>();
    }

    public void setVariable(String variable, String result) {
        this.variables.put(variable, result);
    }

    public String getVariable(String variable) {
        return this.variables.getOrDefault(variable, "");
    }

    public void exit() {
        isClosed = true;
    }

    public boolean isClosed() {
        return isClosed;
    }
}

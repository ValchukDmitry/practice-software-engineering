package ru.ifmo.cli_application.tokens;

import java.util.List;

public interface IExecutable {
    String execute(List<IToken> args, String inputStream);
}

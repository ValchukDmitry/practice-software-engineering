package ru.ifmo.cli_application;

import java.io.IOException;

public class CLI {
    public static void main(String[] args) throws IOException {
        Application application = new Application(System.in, System.out);
        application.start();
    }
}

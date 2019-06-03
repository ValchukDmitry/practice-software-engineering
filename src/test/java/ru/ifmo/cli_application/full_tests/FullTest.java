package ru.ifmo.cli_application.full_tests;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.Test;
import ru.ifmo.cli_application.Application;

import static org.junit.Assert.assertEquals;

public class FullTest {
    private String executeCommand(List<String> command) throws IOException {
        List<String> commands = new ArrayList<>(command);
        commands.add("exit");
        InputStream inputStream = new ByteArrayInputStream(commands.stream().collect(Collectors.joining("\n")).getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Application app = new Application(inputStream, outputStream);
        app.start();
        return new String(outputStream.toByteArray());
    }

    @Test
    public void echoVariableTest() throws IOException {
        assertEquals("\n12 \n\n", executeCommand(Arrays.asList("x=12", "echo $x")));
    }

    @Test
    public void echoVariableConcatTest() throws IOException {
        assertEquals("\n1212 \n\n", executeCommand(Arrays.asList("x=12", "echo 12$x")));
    }

    @Test
    public void echoFromCommand() throws IOException {
        assertEquals("\n\n12 \n\n", executeCommand(Arrays.asList("x=ec", "y=ho", "$x$y 12")));
    }

}

package ru.ifmo.cli_application.commands;

import ru.ifmo.cli_application.tokens.IExecutable;
import ru.ifmo.cli_application.tokens.IToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class for calculation of lines, words and bytes count for each file
 */
public class WcCommand implements IToken, IExecutable {
    @Override
    public String execute(List<IToken> args, String inputStream) {
        if (args.size() == 0) {
            Stats inputStreamStats = new Stats(null, inputStream);
            return inputStreamStats.statisticWithoutFileName();
        }
        List<Stats> stats = new ArrayList<>();
        for (IToken arg : args) {
            try (Scanner scanner = new Scanner(new FileInputStream(arg.getValue()))) {
                StringBuilder fileContent = new StringBuilder();
                while (scanner.hasNextLine()) {
                    fileContent.append(scanner.nextLine());
                    fileContent.append('\n');
                }
                stats.add(new Stats(arg.getValue(), fileContent.toString()));
            } catch (FileNotFoundException e) {
                return "File not found: " + arg.getValue();
            }
        }
        if (stats.size() == 1) {
            return stats.get(0).statisticWithFileName();
        }
        stats.add(new Stats(stats));
        StringBuilder stringBuilder = new StringBuilder();
        for (Stats stat : stats) {
            stringBuilder.append(stat.statisticWithFileName());
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    @Override
    public String getValue() {
        return "wc";
    }

    private class Stats {
        private long linesCount;
        private long wordsCount;
        private long bytesCount;
        private String fileName;

        Stats(List<Stats> stats) {
            fileName = "total";
            for (Stats stat : stats) {
                this.linesCount += stat.linesCount;
                this.wordsCount += stat.wordsCount;
                this.bytesCount += stat.bytesCount;
            }
        }

        Stats(String fileName, String string) {
            linesCount = string.chars().filter(ch -> ch == '\n').count() + 1;
            wordsCount = string.split("[ \n\t\r]+").length;
            bytesCount = string.getBytes().length;
            this.fileName = fileName;
        }

        public String statisticWithoutFileName() {
            return String.format("%d\t%d\t%d", linesCount, wordsCount, bytesCount);
        }

        public String statisticWithFileName() {
            return String.format("%d\t%d\t%d\t%s", linesCount, wordsCount, bytesCount, fileName);
        }
    }
}

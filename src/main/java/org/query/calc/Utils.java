package org.query.calc;

import static org.query.calc.domain.Row.DELIM;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.query.calc.domain.Row;

public class Utils {

    public static List<Row> readRowsFromFile(Path path) throws IOException {
        BufferedReader reader = Files.newBufferedReader(path);
        String line = reader.readLine().trim();
        int lineCount = Integer.parseInt(line);

        return IntStream.range(0, lineCount).mapToObj(lineNum -> {
            try {
                String l = reader.readLine();
                String[] record = l.trim().split(DELIM);
                Double first = Double.parseDouble(record[0]);
                Double second = Double.parseDouble(record[1]);
                return new Row(lineNum, first, second);
            } catch (IOException e) {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));
    }

    public static void writeResultToFile(List<String> lines, Path output) throws IOException {
        Files.write(output, lines);
    }
}

package org.query.calc;

import static org.query.calc.domain.Row.DELIM;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.query.calc.domain.Group;
import org.query.calc.domain.GroupComparator;
import org.query.calc.domain.Row;

public class FileUtils {

    public static List<Row> getValues(Path path) throws IOException {
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

    public static void writeResult(Map<Double, Group> t1Map, Path output) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add(String.valueOf(Math.min(t1Map.size(), 10)));

        //ORDER BY s DESC
        //LIMIT 10
        lines.addAll(t1Map.values().stream().sorted(new GroupComparator()).limit(10)
                .map(r -> r.getValue() + DELIM + r.getSum()).collect(
                        Collectors.toList()));
        Files.write(output, lines);
    }

}

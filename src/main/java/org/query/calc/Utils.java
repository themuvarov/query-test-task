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

    public static TreeMap<Double, List<Double>> cartesianProduct(List<Row> t1, List<Row> t2) {
        TreeMap<Double, List<Double>> result = new TreeMap<>(Double::compareTo);
        Map<Double, List<Double>> map = new HashMap<>();

        for (Row row : t1) {
            for (Row item : t2) {
                Double key = row.getField1() + item.getField1();
                Double value = row.getField2() * item.getField2();

                List<Double> product = map.computeIfAbsent(key,(d) -> new LinkedList<>());
                product.add(value);
                map.put(key, product);
                result.put(key, product);
            }
        }

        return result;
    }

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

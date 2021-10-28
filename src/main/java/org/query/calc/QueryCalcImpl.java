package org.query.calc;

import static org.query.calc.domain.Row.DELIM;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.query.calc.domain.Group;
import org.query.calc.domain.Row;

public class QueryCalcImpl implements QueryCalc {

    @Override
    public void select(Path t1, Path t2, Path t3, Path output) throws IOException {
        // - t1 is a file contains table "t1" with two columns "a" and "x". First line is a number of rows, then each
        //  line contains exactly one row, that contains two numbers parsable by Double.parse(): value for column a and
        //  x respectively.See test resources for examples.
        // - t2 is a file contains table "t2" with columns "b" and "y". Same format.
        // - t3 is a file contains table "t3" with columns "c" and "z". Same format.
        // - output is table stored in the same format: first line is a number of rows, then each line is one row that
        //  contains two numbers: value for column a and s.
        //
        // Number of rows of all three tables lays in range [0, 1_000_000].
        // It's guaranteed that full content of all three tables fits into RAM.
        // It's guaranteed that full outer join of at least one pair (t1xt2 or t2xt3 or t1xt3) of tables can fit into RAM.
        //
        // TODO: Implement following query, put a reasonable effort into making it efficient from perspective of
        //  computation time, memory usage and resource utilization (in that exact order). You are free to use any lib
        //  from a maven central.
        //
        // SELECT a, SUM(X * y * z) AS s FROM 
        // t1 LEFT JOIN (SELECT * FROM t2 JOIN t3) AS t
        // ON a < b + c
        // GROUP BY a
        // STABLE ORDER BY s DESC
        // LIMIT 10;
        // 
        // Note: STABLE is not a standard SQL command. It means that you should preserve the original order. 
        // In this context it means, that in case of tie on s-value you should prefer value of a, with a lower row number.
        // In case multiple occurrences, you may assume that group has a row number of the first occurrence.

        List<Row> t2Rows = Utils.readRowsFromFile(t2);
        List<Row> t3Rows = Utils.readRowsFromFile(t3);
        // SELECT * FROM t2 JOIN t3
        TreeMap<Double, List<Double>> crossJoinResult = Utils.cartesianProduct(t2Rows, t3Rows);

        List<Row> t1Rows = Utils.readRowsFromFile(t1);

        Collection<Group> leftJoinGroups = leftJoin(t1Rows,crossJoinResult);

        Utils.writeResultToFile(sortAndLimit(leftJoinGroups), output);
    }

    private Collection<Group> leftJoin(List<Row> t1Rows, TreeMap<Double, List<Double>> crossJoinResult) {
        //GROUP BY a
        Map<Double, Group> t1Map = new HashMap<>();

        t1Rows.forEach(row -> {
            //LEFT JOIN ON a < b + c
            Double yz = crossJoinResult.higherKey(row.getField1());
            if (yz != null) {
                SortedMap<Double, List<Double>> leftJoin = crossJoinResult.tailMap(yz, true);
                leftJoin.forEach((key, products) -> {
                    products.forEach(rec -> {
                        // x * yz
                        Double xyz = row.getField2() * rec;
                        Group group = t1Map
                                .computeIfAbsent(row.getField1(), k -> new Group(row.getNum(), row.getField1()));
                        // SUM(xyz)
                        group.add(xyz);
                    });
                });
            } else {
                // NULL in join -> xyz := 0
                t1Map.computeIfAbsent(row.getField1(), k -> new Group(row.getNum(), row.getField1()));
            }
        });

        return t1Map.values();
    }

    private List<String> sortAndLimit(Collection<Group> groups) {
        List<String> lines = new ArrayList<>();
        lines.add(String.valueOf(Math.min(groups.size(), 10)));

        //ORDER BY s DESC
        //LIMIT 10
        lines.addAll(groups.stream().sorted(new GroupComparator()).limit(10)
                .map(r -> r.getValue() + DELIM + r.getSum()).collect(
                        Collectors.toList()));
        return lines;
    }
}

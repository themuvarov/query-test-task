package org.query.calc;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.query.calc.domain.Row;

public class Utils {

    public static TreeMap<Double, List<Double>> cartesianProduct(List<Row> t1, List<Row> t2) {
        TreeMap<Double, List<Double>> result = new TreeMap<>(Double::compareTo);
        Map<Double, List<Double>> map = new HashMap<>();

        for (Row row : t1) {
            for (Row item : t2) {
                Double key = row.getField1() + item.getField1();
                Double value = row.getField2() * item.getField2();

                List<Double> product = map.get(key);
                if (product == null) {
                    product = new LinkedList<>();
                }

                product.add(value);
                map.put(key, product);
                result.put(key, product);
            }
        }

        return result;
    }
}

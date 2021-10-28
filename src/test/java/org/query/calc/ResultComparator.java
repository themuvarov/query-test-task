package org.query.calc;

import org.junit.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

public class ResultComparator {
    public static void assertFilesEqual(Supplier<String> testNameSupplier, Path expected, Path actual) throws IOException {
        try (BufferedReader expectedSReader = Files.newBufferedReader(expected);
             BufferedReader actualReader = Files.newBufferedReader(actual)) {

            int lineNumber = 1;
            String line1 = "", line2 = "";
            while ((line1 = expectedSReader.readLine()) != null) {
                line2 = actualReader.readLine();

                Assert.assertNotNull(String.format("Test: %s. Result expected to have line %s", testNameSupplier.get(), lineNumber), line2);
                String[] expectedSplit = line1.split(" ");
                String[] actualSplit = line2.split(" ");

                Assert.assertEquals(
                    String.format("Test: %s. Line %d expected to contain %d numbers", testNameSupplier.get(), lineNumber,  expectedSplit.length),
                    expectedSplit.length,
                    actualSplit.length);

                for (int i = 0; i < expectedSplit.length; ++i) {
                    double expectedParsed = Double.parseDouble(expectedSplit[i]);
                    double actualParsed = Double.parseDouble(actualSplit[i]);

                    Assert.assertEquals(
                        String.format("Test: %s. Line %d deviates from expected", testNameSupplier.get(), lineNumber),
                        expectedParsed,
                        actualParsed,
                        1e-8);
                }

                lineNumber++;
            }
        }
    }

}
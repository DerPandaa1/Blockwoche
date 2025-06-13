package dev.ffuchs.dbbenchmark;

import dev.ffuchs.dbbenchmark.api.BenchmarkResult;
import dev.ffuchs.dbbenchmark.api.DBBenchmarkConnection;
import dev.ffuchs.dbbenchmark.api.Logger;
import dev.ffuchs.dbbenchmark.benchmarks.BatchQueryBenchmark;

import java.util.ArrayList;
import java.util.function.BiFunction;

public class BenchmarkManager {
    public static final int AMOUNT = 100;
    public static final int[] QUERY_COUNTS = new int[]{100, 1000, 10000};
    public static final int[] BATCH_SIZES = new int[]{1, 5, 20, 100};

    private BenchmarkManager() {
    }

    public static void executeBatchBenchmark(DBBenchmarkConnection connection, String outputFolder, String id, BiFunction<Integer, Integer, BenchmarkResult> benchmark) {
        Logger.info("Running " + id + " with " + AMOUNT + " iterations for each query count: " + java.util.Arrays.toString(QUERY_COUNTS));
        long lastTime = System.currentTimeMillis();
        for (var queryCount : QUERY_COUNTS) {
            Logger.info("    Running query counts of " + queryCount);
            for (var batchSize : BATCH_SIZES) {
                var results = new ArrayList<BenchmarkResult>();
                Logger.info("        Running batch size of " + batchSize);
                for (var i = 0; i < AMOUNT; i++) {
                    var currentTime = System.currentTimeMillis();
                    if (currentTime - lastTime > 3000) {
                        lastTime = currentTime;
                        Logger.info("            Iteration " + (i + 1) + " of " + AMOUNT);
                    }
                    results.add(benchmark.apply(queryCount, batchSize));
                }
                String fileName = id + "_queries" + queryCount + "_batchsize" + batchSize + ".csv";
                String filepath = outputFolder + "/" + fileName;
                Logger.info("            Exporting to " + filepath);
                BenchmarkResult.dumpResultsAsCSV(results, filepath);
            }
        }
    }

    public static void BatchQueryBenchmark(DBBenchmarkConnection connection, String outputFolder) {
        executeBatchBenchmark(connection, outputFolder, "BatchQueryBenchmark", (queryCount, batchSize) -> new BatchQueryBenchmark(connection, queryCount, batchSize).runAndResult());
    }
}

package dev.ffuchs.dbbenchmark;

import dev.ffuchs.dbbenchmark.api.BenchmarkResult;
import dev.ffuchs.dbbenchmark.api.DBBenchmarkConnection;
import dev.ffuchs.dbbenchmark.benchmarks.SingleQueryBenchmark;

import java.util.ArrayList;

public class BenchmarkManager {
    private BenchmarkManager(){}

    public static final int AMOUNT = 100;

    public static final int[] QUERY_COUNTS =  new int[] { 1000 };

    public static void SingleQueryBenchmark(DBBenchmarkConnection connection){
        System.out.println("Running SingleQueryBenchmark with " + AMOUNT + " iterations for each query count: " + java.util.Arrays.toString(QUERY_COUNTS));
        for(var queryCount : QUERY_COUNTS) {
            System.out.println("    Running query counts of " + queryCount);
            var results = new ArrayList<BenchmarkResult>();
            for(var i = 0; i < AMOUNT; i++) {
                if((i + 1) % 10 == 0) System.out.println("       Iteration " + (i + 1) + " of " + AMOUNT);
                results.add(new SingleQueryBenchmark(connection, queryCount).runAndResult());
            }
            BenchmarkResult.dumpResultsAsCSV(results, "SingleQueryBenchmark_" + queryCount + ".csv");
        }
    }
}

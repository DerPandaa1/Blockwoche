package dev.ffuchs.dbbenchmark.api;

import java.util.ArrayList;
import java.util.Map;

public record BenchmarkResult(String type, long duration, long queryCount, int batchSize,
                              Map<CrudType, Long> durationPerCrudType) {
    public static void dumpResultsAsCSV(ArrayList<BenchmarkResult> results, String fileName) {
        try (var writer = new java.io.FileWriter(fileName)) {
            writer.write("Benchmark,Total Time (ns),Total Queries,Batch Size,Create Time (ns),Read Time (ns),Update Time (ns),Delete Time (ns)\n");
            for (BenchmarkResult result : results) {
                if (result == null) continue;
                writer.write(String.format("%s,%d,%d,%d,%d,%d,%d,%d\n",
                        result.type(),
                        result.duration(),
                        result.queryCount(),
                        result.batchSize(),
                        result.getTimeForCrudType(CrudType.CREATE),
                        result.getTimeForCrudType(CrudType.READ),
                        result.getTimeForCrudType(CrudType.UPDATE),
                        result.getTimeForCrudType(CrudType.DELETE)));
            }
            writer.write(",\n");
            writer.write("Total Time Average (ms),\"=AVERAGE(B2,B" + (results.size() + 1) + ")/1000000\"\n");
            writer.write("Create Time Average (ms),\"=AVERAGE(E2,E" + (results.size() + 1) + ")/1000000\"\n");
            writer.write("Read Time Average (ms),\"=AVERAGE(F2,F" + (results.size() + 1) + ")/1000000\"\n");
            writer.write("Update Time Average (ms),\"=AVERAGE(G2,G" + (results.size() + 1) + ")/1000000\"\n");
            writer.write("Delete Time Average (ms),\"=AVERAGE(H2,H" + (results.size() + 1) + ")/1000000\"\n");
        } catch (Exception e) {
            System.err.println("Error writing results to CSV: " + e.getMessage());
        }
    }

    public long getTimeForCrudType(CrudType crudType) {
        return durationPerCrudType.getOrDefault(crudType, 0L);
    }
}

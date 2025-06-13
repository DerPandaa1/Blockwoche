package dev.ffuchs.dbbenchmark.api;

import java.util.ArrayList;
import java.util.Map;

public record BenchmarkResult(String type, long duration, long queryCount, Map<CrudType, Long> durationPerCrudType) {
    public void print(){
        System.out.println("------------------------------------------------------------");
        System.out.println("Benchmark Type: " + type);
        System.out.println("Duration: " + duration + " ms");
        System.out.println("Query Count: " + queryCount);
        System.out.println("Duration per CRUD Type:");
        for (var entry : durationPerCrudType.entrySet()) {
            System.out.printf("  %s: %d ms%n", entry.getKey(), entry.getValue() / 1_000_000);
        }
    }

    public long getTimeForCrudType(CrudType crudType) {
        return durationPerCrudType.getOrDefault(crudType, 0L);
    }


    public static void dumpResultsAsCSV(ArrayList<BenchmarkResult> results, String fileName) {
        try (var writer = new java.io.FileWriter(fileName)) {
            writer.write("Benchmark,Total Time (ns),Total Queries,Create Time (ns),Read Time (ns),Update Time (ns),Delete Time (ns)\n");
            for (BenchmarkResult result : results) {
                if (result == null) continue;
                writer.write(String.format("%s,%d,%d,%d,%d,%d,%d\n",
                        result.type(),
                        result.duration(),
                        result.queryCount(),
                        result.getTimeForCrudType(CrudType.CREATE),
                        result.getTimeForCrudType(CrudType.READ),
                        result.getTimeForCrudType(CrudType.UPDATE),
                        result.getTimeForCrudType(CrudType.DELETE)));
            }
        } catch (Exception e) {
            System.err.println("Error writing results to CSV: " + e.getMessage());
        }
    }
}

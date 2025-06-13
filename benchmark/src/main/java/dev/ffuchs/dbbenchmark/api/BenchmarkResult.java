package dev.ffuchs.dbbenchmark.api;

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
}

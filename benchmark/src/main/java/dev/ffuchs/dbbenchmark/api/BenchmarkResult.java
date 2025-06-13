package dev.ffuchs.dbbenchmark.api;

import java.util.HashMap;

public record BenchmarkResult(long duration, long queryCount, HashMap<CrudType, Integer> durationPerCrudType) {

}

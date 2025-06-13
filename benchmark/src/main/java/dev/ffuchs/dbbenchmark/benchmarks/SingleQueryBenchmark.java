package dev.ffuchs.dbbenchmark.benchmarks;

import dev.ffuchs.dbbenchmark.api.Benchmark;
import dev.ffuchs.dbbenchmark.api.DBBenchmarkConnection;

public class SingleQueryBenchmark extends Benchmark {
    public SingleQueryBenchmark(DBBenchmarkConnection connection, int queryCountPerCrudType) {
        super(connection);
    }

    @Override
    public void run() {

    }
}

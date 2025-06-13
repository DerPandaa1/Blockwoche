package dev.ffuchs.dbbenchmark.api;

public abstract class Benchmark implements Runnable {
    protected final DBBenchmarkConnection connection;

    protected BenchmarkResult result;

    public Benchmark(DBBenchmarkConnection connection) {
        this.connection = connection;
    }

    public BenchmarkResult getResult() {
        return result;
    }
}

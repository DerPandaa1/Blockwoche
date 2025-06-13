package dev.ffuchs.dbbenchmark;

import dev.ffuchs.dbbenchmark.api.BenchmarkResult;
import dev.ffuchs.dbbenchmark.api.DBBenchmarkConnection;
import dev.ffuchs.dbbenchmark.benchmarks.SingleQueryBenchmark;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.util.ArrayList;


@Command(name = "HSQL DB Benchamrk", mixinStandardHelpOptions = true, version = "Skibidi", description = "Skibidi Toilet in Ohio Fanum Taxing on the HSQL DB")
public class Main implements Runnable {
    @Option(names = {"-p", "--port"}, description = "The port of the database server", defaultValue = "9001")
    private int port;

    @Option(names = {"-h", "--host"}, description = "The host of the database server", defaultValue = "localhost")
    private String host;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        ArrayList<BenchmarkResult> results = new ArrayList<>();
        try (var connection = new DBBenchmarkConnection(host, port)) {
            results.add(new SingleQueryBenchmark(connection, 100).runAndResult());
            results.add(new SingleQueryBenchmark(connection, 1000).runAndResult());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
        for(var result : results) {
            if(result==null) continue;
            result.print();
        }
    }
}
package dev.ffuchs.dbbenchmark;

import dev.ffuchs.dbbenchmark.api.DBBenchmarkConnection;
import dev.ffuchs.dbbenchmark.api.Logger;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.io.File;
import java.nio.file.Files;

@Command(name = "HSQL DB Benchamrk", mixinStandardHelpOptions = true, version = "Skibidi", description = "Skibidi Toilet in Ohio Fanum Taxing on the HSQL DB")
public class Main implements Runnable {
    @Option(names = {"-p", "--port"}, description = "The port of the database server", defaultValue = "9001")
    private int port;

    @Option(names = {"-h", "--host"}, description = "The host of the database server", defaultValue = "localhost")
    private String host;

    @Option(names = {"-u", "--user"}, description = "The user of the database server", defaultValue = "SA")
    private String user;

    @Option(names = {"-w", "--password"}, description = "The password of the database server", defaultValue = "")
    private String password;

    @Option(names = {"-o" , "--output"}, description = "The output folder for the benchmark results", defaultValue = "./benchmark_results")
    private String outputFolder;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        Logger.setup();
        if(!Files.isDirectory(new File(outputFolder).toPath())) {
            System.err.println("Output folder does not exist: " + outputFolder);
            return;
        }
        try (var connection = new DBBenchmarkConnection(host, port, user, password)) {
            BenchmarkManager.BatchQueryBenchmark(connection, outputFolder);
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
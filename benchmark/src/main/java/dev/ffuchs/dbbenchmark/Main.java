package dev.ffuchs.dbbenchmark;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;


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

    }
}
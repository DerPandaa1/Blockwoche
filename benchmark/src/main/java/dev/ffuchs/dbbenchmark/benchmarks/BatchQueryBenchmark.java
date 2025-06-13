package dev.ffuchs.dbbenchmark.benchmarks;

import dev.ffuchs.dbbenchmark.api.*;

import java.util.Map;

public class BatchQueryBenchmark extends Benchmark {
    private int queryCountPerCrudType;
    private int batchSize;

    public BatchQueryBenchmark(DBBenchmarkConnection connection, int queryCountPerCrudType, int batchSize) {
        super(connection);
        this.queryCountPerCrudType = queryCountPerCrudType;
        this.batchSize = batchSize;
    }

    @Override
    public void run() {
        long timeCreate;
        long timeRead;
        long timeUpdate;
        long timeDelete;

        try (var table = connection.createTempTable()) {
            var commands = FakeData.generateBatchedSQLCommands(queryCountPerCrudType, table.getTableName(), batchSize);

            timeCreate = System.nanoTime();

            for(var cmd: commands.get(CrudType.CREATE)) {
                connection.execute(cmd);
            }

            timeCreate = System.nanoTime() - timeCreate;

            timeRead = System.nanoTime();

            for(var cmd: commands.get(CrudType.READ)) {
                connection.execute(cmd);
            }

            timeRead = System.nanoTime() - timeRead;

            timeUpdate = System.nanoTime();

            for(var cmd: commands.get(CrudType.UPDATE)) {
                connection.execute(cmd);
            }

            timeUpdate = System.nanoTime() - timeUpdate;

            timeDelete = System.nanoTime();

            for(var cmd: commands.get(CrudType.DELETE)) {
                connection.execute(cmd);
            }

            timeDelete = System.nanoTime() - timeDelete;
        }

        result = new BenchmarkResult("BatchQueryBenchmark", timeCreate + timeRead + timeUpdate + timeDelete, queryCountPerCrudType * 4L, batchSize, Map.of(
                CrudType.CREATE, timeCreate,
                CrudType.READ, timeRead,
                CrudType.UPDATE, timeUpdate,
                CrudType.DELETE, timeDelete
        ));
    }
}

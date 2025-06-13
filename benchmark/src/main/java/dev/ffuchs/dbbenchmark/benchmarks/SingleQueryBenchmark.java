package dev.ffuchs.dbbenchmark.benchmarks;

import dev.ffuchs.dbbenchmark.api.*;

import java.util.Map;

public class SingleQueryBenchmark extends Benchmark {
    private int queryCountPerCrudType;

    public SingleQueryBenchmark(DBBenchmarkConnection connection, int queryCountPerCrudType) {
        super(connection);
        this.queryCountPerCrudType = queryCountPerCrudType;
    }

    @Override
    public void run() {
        long timeCreate;
        long timeRead;
        long timeUpdate;
        long timeDelete;

        try (var table = connection.createTempTable()){
            var commands = FakeData.generateSQLCommands(queryCountPerCrudType, table.getTableName());

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

        result = new BenchmarkResult("SingleQueryBenchmark", timeCreate + timeRead + timeUpdate + timeDelete, queryCountPerCrudType * 4, Map.of(
                CrudType.CREATE, timeCreate,
                CrudType.READ, timeRead,
                CrudType.UPDATE, timeUpdate,
                CrudType.DELETE, timeDelete
        ));
    }
}

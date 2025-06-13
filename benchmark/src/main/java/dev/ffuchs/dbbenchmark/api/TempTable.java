package dev.ffuchs.dbbenchmark.api;

import java.util.UUID;

public class TempTable implements AutoCloseable {
    private final DBBenchmarkConnection connection;
    private final String tableName;

    public TempTable(DBBenchmarkConnection connection) {
        this.connection = connection;
        this.tableName = "temp_" + UUID.randomUUID().toString().replaceAll("-", "");
        connection.execute("CREATE TABLE " + tableName + " (uuid VARCHAR(36) PRIMARY KEY, payload VARCHAR(32))");
    }

    @Override
    public void close() {
        connection.execute("DROP TABLE " + tableName);
    }

    public String getTableName() {
        return tableName;
    }
}

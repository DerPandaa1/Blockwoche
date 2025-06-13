package dev.ffuchs.dbbenchmark.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBBenchmarkConnection implements AutoCloseable {
    private final Connection connection;

    public DBBenchmarkConnection(String host, int port, String user, String password) {
        try {
            String jdbcUrl = port == 0
                    ? "jdbc:hsqldb:mem:testdb"
                    : "jdbc:hsqldb:hsql://" + host + ":" + port + "/";

            this.connection = DriverManager.getConnection(jdbcUrl, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to HSQLDB", e);
        }
    }

    public void execute(String sql) {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("SQL execution failed: " + sql, e);
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to close DB connection", e);
        }
    }

    public TempTable createTempTable(){
        return new TempTable(this);
    }
}

package dev.ffuchs.dbbenchmark.api;

import java.util.HashMap;
import java.util.UUID;

public class FakeData {
    private FakeData() {
    }

    public static String randomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = (char) ('a' + Math.random() * 26);
            sb.append(c);
        }
        return sb.toString();
    }

    public static UUID[] generateUUIDs(int count) {
        UUID[] uuids = new UUID[count];
        for (int i = 0; i < count; i++) {
            uuids[i] = UUID.randomUUID();
        }
        return uuids;
    }

    public static HashMap<CrudType, String[]> generateSQLCommands(int amount, String table) {
        HashMap<CrudType, String[]> commands = new HashMap<>();

        String[] insertCommands = new String[amount];
        String[] updateCommands = new String[amount];
        String[] deleteCommands = new String[amount];
        String[] selectCommands = new String[amount];

        for (int i = 0; i < amount; i++) {
            String uuid = UUID.randomUUID().toString();
            insertCommands[i] = "INSERT INTO " + table + " (uuid, payload) VALUES ('" + uuid + "', '" + randomString(32) + "')";
            updateCommands[i] = "UPDATE " + table + " SET payload = '" + randomString(32) + "' WHERE uuid = '" + uuid + "'";
            deleteCommands[i] = "DELETE FROM " + table + " WHERE uuid = '" + uuid + "'";
            selectCommands[i] = "SELECT * FROM " + table + " WHERE uuid = '" + uuid + "'";
        }

        commands.put(CrudType.CREATE, insertCommands);
        commands.put(CrudType.READ, selectCommands);
        commands.put(CrudType.UPDATE, updateCommands);
        commands.put(CrudType.DELETE, deleteCommands);

        return commands;
    }

    public static HashMap<CrudType, String[]> generateBatchedSQLCommands(int amount, String table, int batchSize) {
        var commands = generateSQLCommands(amount, table);

        HashMap<CrudType, String[]> batchedCommands = new HashMap<>();

        for (CrudType type : CrudType.values()) {
            String[] originalCommands = commands.get(type);
            String[] batched = new String[(int) Math.ceil((double) originalCommands.length / batchSize)];

            for (int i = 0; i < originalCommands.length; i += batchSize) {
                StringBuilder batch = new StringBuilder();
                if (batchSize > 1) batch.append("START TRANSACTION;\n");
                for (int j = i; j < Math.min(i + batchSize, originalCommands.length); j++) {
                    batch.append(originalCommands[j]).append(";\n");
                }
                if (batchSize > 1) batch.append("COMMIT;");
                batched[i / batchSize] = batch.toString();
            }

            batchedCommands.put(type, batched);
        }

        return batchedCommands;
    }
}

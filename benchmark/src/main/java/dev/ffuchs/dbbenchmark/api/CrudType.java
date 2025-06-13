package dev.ffuchs.dbbenchmark.api;

public enum CrudType {
    CREATE,
    READ,
    UPDATE,
    DELETE;

    public static CrudType fromString(String type) {
        return switch (type.toUpperCase()) {
            case "CREATE" -> CREATE;
            case "READ" -> READ;
            case "UPDATE" -> UPDATE;
            case "DELETE" -> DELETE;
            default -> throw new IllegalArgumentException("Unknown CRUD type: " + type);
        };
    }
}

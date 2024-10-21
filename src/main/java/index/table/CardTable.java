package index.table;

import index.Database;

import java.sql.SQLException;

public final class CardTable {
    private Database database;

    public CardTable(Database database) {
        this.database = database;
    }

    public void create() throws SQLException {
        var sql = """
                CREATE TABLE IF NOT EXISTS cards (
                    id INTEGER IDENTITY PRIMARY KEY,
                    frontText VARCHAR(255),
                    backText VARCHAR(255)
                )""";
        try (var conn = database.getConnection();
             var stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
}

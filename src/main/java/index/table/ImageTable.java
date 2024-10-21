package index.table;

import index.Database;

import java.sql.SQLException;

public final class ImageTable implements Table {
    private Database database;

    public ImageTable(Database database) {
        this.database = database;
    }

    public void create() {
        var sql = """
                CREATE TABLE IF NOT EXISTS images (
                    uuid CHAR(36) PRIMARY KEY,
                    type VARCHAR(5),
                    cardId INTEGER,
                    FOREIGN KEY (cardId) REFERENCES cards(id) ON DELETE CASCADE
                );""";
        try (var conn = database.getConnection();
             var stmt = conn.createStatement()) {
            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

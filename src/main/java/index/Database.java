package index;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {
    private final Config config;

    Database(Config config) {
        this.config = config;
    }

    public Connection getConnection() throws SQLException {
        var url = config.getProperty("index.database.url");
        return DriverManager.getConnection(url);
    }
}

package index.repository;

import index.Database;
import index.model.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public final class CardRepository implements Repository<Card> {
    private Database database;

    public CardRepository(Database database) {
        this.database = database;
    }

    @Override
    public void add(Card card) throws SQLException {
        var sql = """
                INSERT INTO cards (frontText, backText)
                VALUES (?, ?);
                """;

        try (var conn = database.getConnection()) {
            try (var pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                conn.setAutoCommit(false);
                pstmt.setString(1, card.getFrontText());
                pstmt.setString(2, card.getBackText());
                pstmt.executeUpdate();

                var rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int cardId = rs.getInt(1);
                    for (var uuid : card.getFrontImages()) {
                        addImage(conn, uuid, "front", cardId);
                    }
                    for (var uuid : card.getBackImages()) {
                        addImage(conn, uuid, "back", cardId);
                    }
                }

                conn.commit();

            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    private void addImage(Connection conn, String uuid, String type, int cardId) throws SQLException {
        var sql = """
                INSERT INTO images (uuid, type, cardId)
                VALUES (?, ?, ?);
                """;

        try (var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, uuid);
            pstmt.setString(2, type);
            pstmt.setInt(3, cardId);
            pstmt.executeUpdate();
        }
    }

    @Override
    public void update(Card card) {

    }

    @Override
    public void remove(Card card) {

    }

    @Override
    public Card findById(long id) {
        return null;
    }

    @Override
    public List<Card> findAll() {
        return List.of(new Card());
    }
}

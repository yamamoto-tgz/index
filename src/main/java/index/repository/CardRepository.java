package index.repository;

import index.Database;
import index.model.Card;

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
        try (var conn = database.getConnection();
             var ps = conn.prepareStatement(sql)) {
            ps.setString(1, card.getFrontText());
            ps.setString(2, card.getBackText());
            ps.execute();
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

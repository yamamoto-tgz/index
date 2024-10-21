package index.repository;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {
    void add(T t) throws SQLException;

    void update(T t) throws SQLException;

    void remove(T t) throws SQLException;

    T findById(long id) throws SQLException;

    List<T> findAll() throws SQLException;
}

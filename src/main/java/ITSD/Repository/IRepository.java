package ITSD.Repository;

import java.util.List;

public interface IRepository<T> extends AutoCloseable {
    List<T> getAll();

    T get(int id);
    T get(String id);

    void insert(T item);

    void update(T item);

    void delete(T item);
    void delete(int id);
    void delete(String id);
}

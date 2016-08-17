package dao;

import model.Entry;

public interface EntryDao {

    void begin();

    void commit();

    void rollback();

    void insert(Entry entry);

    void update(Entry entry);

    Entry getById(long id);

    void deleteById(long id);
}

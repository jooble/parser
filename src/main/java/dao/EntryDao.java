package dao;

import model.Entry;

import java.util.List;

public interface EntryDao {

    void insert(Entry entry);

    void update(Entry entry);

    List<Entry> getAll();

    Entry getById(long id);

    void deleteById(long id);
}

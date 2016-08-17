package dao;

import model.Entry;

import java.util.List;

public class EntryDaoImpl implements EntryDao {
    @Override
    public void insert(Entry entry) {
        System.out.println("Entry - ID был добавлен в базу данных " + entry + " поток выполнения " + Thread.currentThread().getName());
    }

    @Override
    public void update(Entry entry) {

    }

    @Override
    public List<Entry> getAll() {
        return null;
    }

    @Override
    public Entry getById(long id) {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }
}

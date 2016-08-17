package dao;

import model.Entry;
import org.hibernate.SessionFactory;

public class EntryDaoImpl implements EntryDao {

    //TODO logger

    private SessionFactory sessionFactory;

    public EntryDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void begin() {
        sessionFactory.getCurrentSession().beginTransaction();
    }

    @Override
    public void commit() {
        sessionFactory.getCurrentSession().getTransaction().commit();
    }

    @Override
    public void rollback() {
        sessionFactory.getCurrentSession().getTransaction().rollback();
    }

    @Override
    public void insert(Entry entry) {
        sessionFactory.getCurrentSession().save(entry);
    }

    @Override
    public void update(Entry entry) {
        sessionFactory.getCurrentSession().update(entry);
    }

    @Override
    public Entry getById(long id) {
        return sessionFactory.getCurrentSession().get(Entry.class, id);
    }

    @Override
    public void deleteById(long id) {
        sessionFactory.getCurrentSession().delete(getById(id));
    }
}

package cz.cvut.nss.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public abstract class AbstractDao<T>
{
    @PersistenceContext
    protected EntityManager em;

    AbstractDao(EntityManager em)
    {
        this.em = em;
    }

    abstract public T get(int id);
    abstract public List<T> getAll();
    abstract public void add(T entity);
    abstract public T update(T entity);
    abstract public void remove(T entity);
}

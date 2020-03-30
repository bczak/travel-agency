package cz.cvut.nss.dao;

import cz.cvut.nss.model.Trip;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

@Repository
public class TripDao extends AbstractDao<Trip> {
    TripDao(EntityManager em) {
        super(em);
    }

    @Override
    public Trip get(int id) {
        return em.find(Trip.class, id);
    }

    @Override
    public List<Trip> getAll() {
        return em.createNamedQuery("Trip.getAll").getResultList();
    }

    @Override
    public void add(Trip entity) {
        Objects.requireNonNull(entity);
        em.persist(entity);
    }

    @Override
    public Trip update(Trip entity) {
        Objects.requireNonNull(entity);
        return em.merge(entity);
    }

    @Override
    public void remove(Trip entity) {
        Objects.requireNonNull(entity);
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }
}

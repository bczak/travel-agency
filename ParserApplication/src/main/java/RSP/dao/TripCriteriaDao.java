package RSP.dao;

import RSP.model.TripCriteria;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

@Repository
public class TripCriteriaDao extends AbstractDao<TripCriteria> {

    TripCriteriaDao(EntityManager em) {
        super(em);
    }

    @Override
    public TripCriteria get(int id) {
        return em.find(TripCriteria.class, id);
    }

    @Override
    public List<TripCriteria> getAll() {
        return em.createNamedQuery("TripCriteria.getAll", TripCriteria.class).getResultList();
    }

    @Override
    public void add(TripCriteria entity) {
        Objects.requireNonNull(entity);
        em.persist(entity);
    }

    @Override
    public TripCriteria update(TripCriteria entity) {
        Objects.requireNonNull(entity);
        return em.merge(entity);
    }

    @Override
    public void remove(TripCriteria entity) {
        Objects.requireNonNull(entity);
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }
}

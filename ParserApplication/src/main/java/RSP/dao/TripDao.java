package RSP.dao;

import RSP.dto.SortAttribute;
import RSP.dto.SortOrder;
import RSP.model.Trip;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

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
        return em.createNamedQuery("Trip.getAll", Trip.class).getResultList();
    }

    public List<Trip> getAllSorted(SortAttribute by, SortOrder order) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Trip> criteria = builder.createQuery(Trip.class);
        Root<Trip> trips = criteria.from(Trip.class);
        Path<?> column = trips.get(by.getColumnName());
        Order ordering = (order == SortOrder.ASCENDING)
                ? builder.asc(column) : builder.desc(column);
        criteria.select(trips).orderBy(ordering);
        TypedQuery<Trip> query = em.createQuery(criteria);
        return query.getResultList();
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

    public Trip getByName(String name) {
        return em.createNamedQuery("Trip.getByName", Trip.class)
                .setParameter("Name", name)
                .setMaxResults(1)
                .getResultList()
                .stream().findFirst().orElse(null);
    }
    public List<Trip> getByPriceASC() {
        TypedQuery<Trip> query = em.createNamedQuery("Trip.getByPriceASC", Trip.class);
        return query.getResultList();
    }

    public List<Trip> getByStartDate() {
        TypedQuery<Trip> query = em.createNamedQuery("Trip.getByStartDate", Trip.class);
        return query.getResultList();
    }

    public List<Trip> getByLength() {
        TypedQuery<Trip> query = em.createNamedQuery("Trip.getByLength", Trip.class);
        return query.getResultList();
    }

    public List<Trip> getByNameSort() {
        TypedQuery<Trip> query = em.createNamedQuery("Trip.getBySortName", Trip.class);
        return query.getResultList();
    }
}

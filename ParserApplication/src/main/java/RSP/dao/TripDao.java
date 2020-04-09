package RSP.dao;

import RSP.dto.SortAttribute;
import RSP.dto.SortOrder;
import RSP.dto.TripsQueryCriteria;
import RSP.model.Trip;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
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

    public List<Trip> getSome(TripsQueryCriteria criteria) {
        // Fetching
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Trip> query = builder.createQuery(Trip.class);
        Root<Trip> trips = query.from(Trip.class);

        // Filtering
        Predicate predicate = builder.conjunction();

        // - by price
        Integer minPrice = criteria.getMinPrice();
        Integer maxPrice = criteria.getMaxPrice();
        if (minPrice != null || maxPrice != null) {
            Path<Integer> price = trips.get("price");
            if (minPrice != null) {
                predicate = builder.and(predicate,
                        builder.greaterThanOrEqualTo(price, minPrice));
            }
            if (maxPrice != null) {
                predicate = builder.and(predicate,
                        builder.lessThanOrEqualTo(price, maxPrice));
            }
        }

        // Sorting
        Path<?> orderByColumn = trips.get(criteria.getSortBy().getColumnName());
        Order ordering = (SortOrder.ASCENDING == criteria.getOrder())
                ? builder.asc(orderByColumn) : builder.desc(orderByColumn);

        // Selecting results
        return em.createQuery(query.select(trips).where(predicate).orderBy(ordering))
                .getResultList();
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
}

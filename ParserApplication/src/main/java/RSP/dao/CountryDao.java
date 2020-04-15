package RSP.dao;

import RSP.model.Country;
import RSP.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

@Repository
public class CountryDao extends AbstractDao<Country>{
    CountryDao(EntityManager em) {
        super(em);
    }

    @Override
    public Country get(int id) {
        return em.find(Country.class, id);
    }

    @Override
    public List<Country> getAll() {
        return em.createNamedQuery("Country.getAll", Country.class).getResultList();
    }

    @Override
    public void add(Country entity) {
        Objects.requireNonNull(entity);
        em.persist(entity);
    }

    @Override
    public Country update(Country entity) {
        Objects.requireNonNull(entity);
        return em.merge(entity);
    }

    @Override
    public void remove(Country entity) {
        Objects.requireNonNull(entity);
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    public Country getByName(String name) {
        return em.createNamedQuery("Country.getByName", Country.class)
                .setParameter("name", name)
                .setMaxResults(1)
                .getResultList()
                .stream().findFirst().orElse(null);
    }
}

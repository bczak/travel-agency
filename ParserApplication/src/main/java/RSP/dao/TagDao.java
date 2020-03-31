package RSP.dao;

import RSP.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

@Repository
public class TagDao extends AbstractDao<Tag>{
    TagDao(EntityManager em) {
        super(em);
    }

    @Override
    public Tag get(int id) {
        return em.find(Tag.class, id);
    }

    @Override
    public List<Tag> getAll() {
        return em.createNamedQuery("Tag.getAll").getResultList();
    }

    @Override
    public void add(Tag entity) {
        Objects.requireNonNull(entity);
        em.persist(entity);
    }

    @Override
    public Tag update(Tag entity) {
        Objects.requireNonNull(entity);
        return em.merge(entity);
    }

    @Override
    public void remove(Tag entity) {
        Objects.requireNonNull(entity);
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }
}

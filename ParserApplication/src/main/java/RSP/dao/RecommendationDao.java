package RSP.dao;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import RSP.model.Recommendation;


@Repository
public class RecommendationDao extends AbstractDao<Recommendation> {

	RecommendationDao(EntityManager em) {
		super(em);
	}

	@Override
	public Recommendation get(int id) {
		return em.find(Recommendation.class, id);
	}

	@Override
	public List<Recommendation> getAll() {
		return em.createNamedQuery("Recommendation.getAll", Recommendation.class)
				.getResultList();
	}

	public List<Recommendation> getUndelivered() {
		return em.createNamedQuery("Recommendation.getUndelivered", Recommendation.class)
				.getResultList();
	}

	@Override
	public void add(Recommendation entity) {
		Objects.requireNonNull(entity);
		em.persist(entity);
	}

	@Override
	public Recommendation update(Recommendation entity) {
		Objects.requireNonNull(entity);
		return em.merge(entity);
	}

	@Override
	public void remove(Recommendation entity) {
		Objects.requireNonNull(entity);
		em.remove(em.contains(entity) ? entity : em.merge(entity));
	}

	public void removeAll() {
		em.createNamedQuery("Recommendation.removeAll").executeUpdate();
	}
}

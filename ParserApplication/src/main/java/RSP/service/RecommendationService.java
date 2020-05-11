package RSP.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import RSP.dao.RecommendationDao;
import RSP.model.Recommendation;


@Service
@Transactional
public class RecommendationService {

	private RecommendationDao recommendationDao;

	@Autowired
	public RecommendationService(RecommendationDao recommendationDao) {
		this.recommendationDao = recommendationDao;
	}

	public Recommendation get(int id) throws RecommendationNotFoundException {
		Recommendation recommendation = recommendationDao.get(id);
		if (recommendation == null) {
			throw new RecommendationNotFoundException(id);
		}
		return recommendation;
	}

	public void add(Recommendation recommendation) {
		Objects.requireNonNull(recommendation);
		if (recommendation.getDate() == null) {
			recommendation.setDate(new Date());
		}
		recommendationDao.add(recommendation);
	}

	public void remove(int id) throws RecommendationNotFoundException {
		Recommendation recommendation = recommendationDao.get(id);
		if (recommendation == null) {
			throw new RecommendationNotFoundException(id);
		}
		recommendationDao.remove(recommendation);
	}

	public List<Recommendation> getAll() {
		return recommendationDao.getAll();
	}

	public List<Recommendation> getUndelivered() {
		return recommendationDao.getUndelivered();
	}

	public void removeAll() {
		recommendationDao.removeAll();
	}

	public void markDelivered(int id) throws RecommendationNotFoundException {
		Recommendation recommendation = get(id);
		if (!recommendation.isDelivered()) {
			recommendation.setDelivered(true);
			recommendationDao.update(recommendation);
		}
	}
}

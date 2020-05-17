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
    private TripService tripService;
    private TripCriteriaService criteriaService;

    @Autowired
    public RecommendationService(
            RecommendationDao recommendationDao,
            TripService tripService,
            TripCriteriaService criteriaService) {
        this.recommendationDao = recommendationDao;
        this.tripService = tripService;
        this.criteriaService = criteriaService;
    }

    public Recommendation get(int id) throws RecommendationNotFoundException {
        Recommendation recommendation = recommendationDao.get(id);
        if (recommendation == null) {
            throw new RecommendationNotFoundException(id);
        }
        return recommendation;
    }

    public void add(Recommendation recommendation)
            throws TripNotFoundException, TripCriteriaNotFoundException {

        Objects.requireNonNull(recommendation);

        recommendation.setTrip(tripService.get(
            Objects.requireNonNull(recommendation.getTrip()).getId()));
        recommendation.setCriteria(criteriaService.get(
            Objects.requireNonNull(recommendation.getCriteria()).getId()));

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

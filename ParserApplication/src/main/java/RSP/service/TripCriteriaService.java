package RSP.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import RSP.dao.RecommendationDao;
import RSP.dao.TripCriteriaDao;
import RSP.dto.TripsQueryCriteria;
import RSP.model.Country;
import RSP.model.Recommendation;
import RSP.model.Tag;
import RSP.model.Trip;
import RSP.model.TripCriteria;

@Service
@Transactional
public class TripCriteriaService {

    private TripCriteriaDao criteriaDao;
    private TripService tripService;
    private CountryService countryService;
    private TagService tagService;
    private RecommendationDao recommendationDao;

    @Autowired
    public TripCriteriaService(
            TripCriteriaDao criteriaDao,
            TripService tripService,
            CountryService countryService,
            TagService tagService,
            RecommendationDao recommendationDao) {
        this.criteriaDao = criteriaDao;
        this.tripService = tripService;
        this.countryService = countryService;
        this.tagService = tagService;
        this.recommendationDao = recommendationDao;
    }

    public void add(TripCriteria criteria)
            throws InvalidQueryException, InconsistentQueryException {
        Objects.requireNonNull(criteria, "trip criteria must not be null");

        List<Country> countries = criteria.getCountries();
        if (countries != null) {
            criteria.setCountries(countries.stream()
                    .map(country -> countryService.getByName(country.getName()))
                    .collect(Collectors.toList()));
        }

        List<Tag> tags = criteria.getTags();
        if (tags != null) {
            criteria.setTags(tags.stream()
                    .map(tag -> tagService.getByName(tag.getName()))
                    .collect(Collectors.toList()));
        }

        criteriaDao.add(criteria);

        for (Trip trip : tripService.getSome(translate(criteria))) {
            Recommendation recommendation = new Recommendation();
            recommendation.setCriteria(criteria);
            recommendation.setTrip(trip);
            recommendation.setDate(new Date());
            recommendation.setDelivered(false);
            recommendationDao.add(recommendation);
        }
    }

    public TripCriteria get(int id) throws TripCriteriaNotFoundException {
        TripCriteria criteria = criteriaDao.get(id);
        if (criteria == null) {
            throw new TripCriteriaNotFoundException(id);
        }
        return criteria;
    }

    public List<Trip> getSelectedTrips(int id)
            throws TripCriteriaNotFoundException,
                    InvalidQueryException,
                    InconsistentQueryException {
        TripCriteria criteria = get(id);
        return tripService.getSome(translate(criteria));
    }

    public TripsQueryCriteria translate(TripCriteria criteria) {
        TripsQueryCriteria result = new TripsQueryCriteria();

        List<Country> countries = criteria.getCountries();
        if (countries != null && !countries.isEmpty()) {
            List<String> countryNames = new ArrayList<>(countries.size());
            for (Country country : countries) {
                countryNames.add(country.getName());
            }
            result.setCountry(countryNames);
        }
        result.setCountryAny(criteria.getAnyCountry() == Boolean.TRUE);

        List<Tag> tags = criteria.getTags();
        if (tags != null && !tags.isEmpty()) {
            List<String> tagNames = new ArrayList<>(tags.size());
            for (Tag tag : tags) {
                tagNames.add(tag.getName());
            }
            result.setTag(tagNames);
        }
        result.setTagAny(criteria.getAnyTag() == Boolean.TRUE);

        result.setMinPrice(criteria.getMinPrice());
        result.setMaxPrice(criteria.getMaxPrice());
        result.setMinLength(criteria.getMinDuration());
        result.setMaxLength(criteria.getMaxDuration());
        result.setStartBefore(criteria.getStartBefore());
        result.setStartAfter(criteria.getStartAfter());
        result.setEndBefore(criteria.getEndBefore());
        result.setEndAfter(criteria.getEndAfter());
        result.setInName(criteria.getInName());
        return result;
    }

    public void remove(int id) throws TripCriteriaNotFoundException {
        TripCriteria criteria = criteriaDao.get(id);
        if (criteria == null) {
            throw new TripCriteriaNotFoundException(id);
        }
        criteriaDao.remove(criteria);
    }

    // BULK OPERATIONS

    public List<TripCriteria> getAll() {
        return criteriaDao.getAll();
    }

    public void removeAll() {
        criteriaDao.removeAll();
    }
}

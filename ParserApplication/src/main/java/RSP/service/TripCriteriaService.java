package RSP.service;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import RSP.dao.TripCriteriaDao;
import RSP.model.TripCriteria;

@Service
@Transactional
public class TripCriteriaService {

    private TripCriteriaDao criteriaDao;

    @Autowired
    public TripCriteriaService(TripCriteriaDao criteriaDao) {
        this.criteriaDao = criteriaDao;
    }

    public void add(TripCriteria criteria) {
        Objects.requireNonNull(criteria, "trip criteria must not be null");
        criteriaDao.add(criteria);
    }

    public TripCriteria get(int id) throws TripCriteriaNotFoundException {
        TripCriteria criteria = criteriaDao.get(id);
        if (criteria == null) {
            throw new TripCriteriaNotFoundException(id);
        }
        return criteria;
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

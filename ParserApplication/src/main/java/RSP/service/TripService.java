package RSP.service;

import RSP.dao.TripDao;
import RSP.dto.SortAttribute;
import RSP.dto.SortOrder;
import RSP.dto.TripsQueryCriteria;
import RSP.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class TripService {

    private TripDao tripDao;

    @Autowired
    public TripService(TripDao tripDao) {
        this.tripDao = tripDao;
    }

    public Trip get(int id) throws TripNotFoundException {
        Trip trip = tripDao.get(id);
        if (trip == null) {
            throw new TripNotFoundException(id);
        }
        return trip;
    }

    public Trip add(Trip trip) {
        Objects.requireNonNull(trip, "trip must not be null");
        Trip oldTrip = tripDao.getByName(trip.getName());
        if (oldTrip == null) {
            trip.setLength(computeLengthOfTrip(trip));
            tripDao.add(trip);
        }
        return oldTrip;
    }

    public boolean idExists(int id) {
        return tripDao.get(id) != null;
    }

    public boolean nameExists(String name) {
        return tripDao.getByName(name) != null;
    }

    public void remove(int id) throws TripNotFoundException {
        Trip trip = tripDao.get(id);
        if (trip == null) {
            throw new TripNotFoundException(id);
        }
        tripDao.remove(trip);
    }

    public List<Trip> getAll() {
        return tripDao.getAll();
    }

    public List<Trip> getAllSorted(SortAttribute by, SortOrder order) {
        return tripDao.getAllSorted(by, order);
    }

    public List<Trip> getSome(TripsQueryCriteria criteria)
            throws InvalidQueryException, InconsistentQueryException {

        Integer minPrice = criteria.getMinPrice();
        if (minPrice != null && minPrice < 0) {
            throw new InvalidQueryException("minPrice", minPrice);
        }

        Integer maxPrice = criteria.getMaxPrice();
        if (maxPrice != null && maxPrice < 0) {
            throw new InvalidQueryException("maxPrice", maxPrice);
        }

        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            throw new InconsistentQueryException("minPrice", minPrice, "maxPrice", maxPrice);
        }

        return tripDao.getSome(criteria);
    }

    public Trip getByName(String name) throws TripNotFoundException {
        Trip trip = tripDao.getByName(name);
        if (trip == null) {
            throw new TripNotFoundException(name);
        }
        return trip;
    }

    private long computeLengthOfTrip(Trip trip) {
        long diffInMillies = Math.abs(
                trip.getEndDate().getTime() - trip.getStartDate().getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return diff;
    }
}

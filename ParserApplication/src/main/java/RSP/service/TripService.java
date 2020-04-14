package RSP.service;

import RSP.dao.TagDao;
import RSP.dao.TripDao;
import RSP.dto.SortAttribute;
import RSP.dto.SortOrder;
import RSP.dto.TripsQueryCriteria;
import RSP.model.Tag;
import RSP.model.Trip;
import RSP.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class TripService {

    private TripDao tripDao;

    private TagDao tagDao;

    @Autowired
    public TripService(TripDao tripDao, TagDao tagDao) {
        this.tripDao = tripDao;
        this.tagDao = tagDao;
    }

    public boolean addTags(Tag tag, int id) throws TripNotFoundException {
        Trip t = tripDao.get(id);
        if(tag == null){
            return false;
        }

        tagDao.add(tag);


        t.getTags().add(tag);

        tripDao.update(t);
        return true;
    }

    public boolean removeTag(int tagId, int tripId) throws TripNotFoundException {
        Tag tag = tagDao.get(tagId);
        Trip trip = tripDao.get(tripId);
        if(tag == null || trip == null){
            return false;
        }
        trip.getTags().remove(tag);
        tagDao.remove(tag);
        tripDao.update(trip);
        return true;
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

    public List<User> getUsersFromTrip(int id) throws TripNotFoundException {
        Trip t = tripDao.get(id);
        if (t == null) {
            throw new TripNotFoundException(id);
        }
        List<User> users = t.getUsers();

        return users;
    }

    private long computeLengthOfTrip(Trip trip) {
        long diffInMillies = Math.abs(
                trip.getEndDate().getTime() - trip.getStartDate().getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return diff;
    }

    // BULK OPERATIONS

    public List<Trip> getAll() {
        return tripDao.getAll();
    }

    public List<Trip> addAll(List<Trip> trips) {
        List<Trip> conflict = new ArrayList<>();
        for (Trip trip : trips) {
            Trip old = add(trip);
            if (old != null) {
                conflict.add(old);
            }
        }
        return conflict;
    }

    public List<Trip> setAll(List<Trip> trips) {
        Map<String, Trip> data = new LinkedHashMap<>();
        List<Trip> conflict = new ArrayList<>();
        for (Trip trip : trips) {
            if (data.putIfAbsent(trip.getName(), trip) != null) {
                conflict.add(trip);
            }
        }
        if (conflict.isEmpty()) {
            removeAll();
            for (Trip trip : data.values()) {
                tripDao.add(trip);
            }
        }
        return conflict;
    }

    public List<Trip> updateAll(List<Trip> trips) {
        List<Trip> result = new ArrayList<>();
        for (Trip trip : trips) {
            Trip original = tripDao.getByName(trip.getName());
            if (original == null) {
                tripDao.add(trip);
            } else {
                trip.setId(original.getId());
                trip = tripDao.update(trip);
            }
            result.add(trip);
        }
        return result;
    }

    public void removeAll() {
        tripDao.removeAll();
    }
}

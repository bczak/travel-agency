package RSP.service;

import RSP.dao.TripDao;
import RSP.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class TripService {

    private TripDao tripDao;

    @Autowired
    public TripService(TripDao tripDao) {
        this.tripDao = tripDao;
    }

    public boolean add(Trip trip) {
        Objects.requireNonNull(trip, "trip must not be null");
        if(idExists(trip.getId()) || nameExists(trip.getName())) {
            return false;
        }
        tripDao.add(trip);
        return true;
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

    public Trip get(int id) throws TripNotFoundException {
        Trip trip = tripDao.get(id);
        if (trip == null) {
            throw new TripNotFoundException(id);
        }
        return trip;
    }

    public List<Trip> getAll() {
        return tripDao.getAll();
    }

    public Trip getByName(String name) throws TripNotFoundException {
        Trip trip = tripDao.getByName(name);
        if (trip == null) {
            throw new TripNotFoundException(name);
        }
        return trip;
    }

    public List<Trip> getByPriceASC()
    {
        return tripDao.getByPriceASC();
    }
}

package RSP.service;

import RSP.dao.TripDao;
import RSP.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TripService {
    private TripDao tripDao;

    @Autowired
    public TripService(TripDao tripDao) {
        this.tripDao = tripDao;
    }

    public boolean add(Trip trip) {
        if(trip == null)
            throw new IllegalArgumentException("Trip can not be Null.");
        if(alreadyExists(trip.getId()) || getByName(trip.getName()) != null)
            return false;
        tripDao.add(trip);
        return true;
    }

    public boolean alreadyExists(int id) {
        return tripDao.get(id) != null;
    }

    public boolean remove(int id) {
        Trip trip = tripDao.get(id);
        if(trip == null)
            throw new IllegalArgumentException("Trip can not be Null.");
        if(!alreadyExists(trip.getId()))
            return false;
        tripDao.remove(trip);
        return true;
    }

    public Trip get(int id) {
        if(!alreadyExists(id))
            throw new IllegalArgumentException("Trip can not be Null.");
        return tripDao.get(id);
    }

    public List<Trip> getAll() {
        return tripDao.getAll();
    }

    public Trip getByName(String name) {
        return tripDao.getByName(name);
    }
}

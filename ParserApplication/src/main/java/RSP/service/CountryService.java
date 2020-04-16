package RSP.service;

import RSP.dao.CountryDao;
import RSP.dao.TripDao;
import RSP.model.Country;
import RSP.model.Tag;
import RSP.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class CountryService {
    private CountryDao countryDao;
    private TripDao tripDao;

    @Autowired
    public CountryService(CountryDao countryDao, TripDao tripDao) {
        this.countryDao = countryDao;
        this.tripDao = tripDao;
    }



    public boolean addTrip(Trip addtrip, int id) throws TripNotFoundException {
        Country c = countryDao.get(id);
        Trip t = tripDao.getByName(addtrip.getName());
        if(c == null){
            return false;
        }
        else if(t != null){
            List<Trip> trips = c.getTrip();
            for (Trip a:
                 trips) {
                if(a.getName().equals(t.getName())){
                    return false;
                }
            }
            c.getTrip().add(t);
            countryDao.update(c);
            return true;
        }else{
            tripDao.add(addtrip);
            c.getTrip().add(addtrip);
            countryDao.update(c);
            return true;
        }
    }

    public boolean removeTrip(int countryId, int tripId) {
        Country country = countryDao.get(countryId);
        Trip trip = tripDao.get(tripId);
        if(country == null || trip == null){
            return false;
        }
        country.getTrip().remove(trip);
        //musim delete tag z DB taky?
        //tagDao.remove(tag);
        countryDao.update(country);
        return true;
    }


    public List<Country> addAll(List<Country> countries) {
        List<Country> conflict = new ArrayList<>();
        for (Country t : countries) {
            Country old = add(t);
            if (old != null) {
                conflict.add(old);
            }
        }
        return conflict;
    }

    public Country getByName(String name) {
        return countryDao.getByName(name);
    }

    public List<Trip> getTrips(int id) {
        return countryDao.getTrips(id);
    }
    
    //CRUD
    public Country add(Country country) {
        Objects.requireNonNull(country, "country must not be null");
        Country oldTag = countryDao.getByName(country.getName());
        if (oldTag == null) {
            countryDao.add(country);
            return null;
        }
        return oldTag;
    }

    public Country get(int id) {
        return countryDao.get(id);
    }

    public Country update(Country country) {
        if (country != null) {
            return countryDao.update(country);
        }
        return null;
    }

    public List<Country> getAll() {
        return countryDao.getAll();
    }

    public void remove(int id) {
        Country country = countryDao.get(id);
        if (country == null) {
            throw new IllegalArgumentException("country must not be null");
        }
        countryDao.remove(country);
    }
}

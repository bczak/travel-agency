package RSP.service;

import RSP.dao.CountryDao;
import RSP.model.Country;
import RSP.model.Tag;
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

    @Autowired
    public CountryService(CountryDao countryDao) {
        this.countryDao = countryDao;
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
    
    //CRUD
    public Country add(Country country) {
        Objects.requireNonNull(country, "country must not be null");
        Country oldTag = countryDao.getByName(country.getName());
        if (oldTag == null) {
            countryDao.add(country);
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

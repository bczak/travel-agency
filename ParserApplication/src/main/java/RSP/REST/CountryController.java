package RSP.REST;

import RSP.model.Country;
import RSP.model.Trip;
import RSP.service.CountryService;
import RSP.service.TripNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    CountryService countryService;

    private static final Logger log = Logger.getLogger(CountryController.class.getName());

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Country> getAll() {
        log.info("REST GET /countries invoked");
        List<Country> result = countryService.getAll();
        log.info(() -> "REST GET /countries returned OK with " + result.size() + " countries");
        return result;
    }

    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    Country get(@PathVariable String name) {
        log.info(() -> "REST GET /countries/{name} invoked with name = " + name);
        Country result = countryService.getByName(name);
        log.info(() -> "REST GET /countries/{name} returned OK with country = " + result);
        return result;
    }

    @GetMapping(value = "/tripGet/{countryid}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Trip> getTripsFromCountry(@PathVariable int countryid) {
        return countryService.getTrips(countryid);
    }

    @PostMapping(value = "/addAll", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Country>> postBulk(@RequestBody List<Country> countries) {
        log.info(() -> "REST POST /countries/addAll invoked with "
                + countries.size() + " countries");
        List<Country> old = countryService.addAll(countries);
        if (old.isEmpty()) {
            log.info(() -> "REST POST /countries/addAll returned CREATED with "
                    + countries.size() + " countries");
            return ResponseEntity
                    .created(URI.create("/countries"))
                    .body(countries);
        } else {
            log.info(() -> "REST POST /countries/addAll returned CONFLICT with "
                    + old.size() + " countries");
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .header("Content-Location", "/countries")
                    .body(old);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Country> add(@RequestBody Country country) {
        log.info("REST POST /countries invoked");
        Country c = countryService.add(country);
        if (c == null) {
            log.info("REST POST /countries returned CREATED");
            return ResponseEntity
                    .created(URI.create("/countries/" + country.getId()))
                    .body(country);
        } else {
            log.info("REST POST /countries returned CONFLICT");
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .header("Content-Location", "/countries/" + c.getId())
                    .body(c);
        }
    }

    @PostMapping(value = "/trip/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Trip> addTrip(@PathVariable int id, @RequestBody Trip trip)
            throws TripNotFoundException {
        log.info(() -> "REST POST /countries/trip/{id} with id = " + id);
        if (!countryService.addTrip(trip, id)) {
            log.info("REST POST /countries/trip/{id} returned CONFLICT");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            log.info("REST POST /countries/trip/{id} returned CREATED");
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void remove(@PathVariable int id) {
        log.info(() -> "REST DELETE /countries/{id} with id = " + id);
        countryService.remove(id);
        log.info("REST DELETE /countries/{id} returned NO_CONTENT");
    }

    @DeleteMapping(value = "/trip/{countryId}/{tripId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> removeTrip(@PathVariable int countryId, @PathVariable int tripId) {
        log.info(() -> "REST DELETE /countries/trip/{countryId}/{tripId} invoked with countryId = "
                + countryId + " and tripId = " + tripId);
        if (countryService.removeTrip(countryId, tripId)) {
            log.info("REST DELETE /countries/trip/{countryId}/{tripId} returned OK");
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            log.info("REST DELETE /countries/trip/{countryId}/{tripId} returned FORBIDDEN");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}

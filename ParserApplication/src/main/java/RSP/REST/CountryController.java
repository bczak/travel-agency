package RSP.REST;

import RSP.model.Country;
import RSP.model.Tag;
import RSP.model.Trip;
import RSP.service.CountryService;
import RSP.service.TripNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {
    CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Country> getAll(){
        return countryService.getAll();
    }

    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    Country get(@PathVariable String name){
        return countryService.getByName(name);
    }

    @GetMapping(value = "/tripGet/{countryid}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Trip> getTripsFromCountry(@PathVariable int countryid){
        return countryService.getTrips(countryid);
    }


    @PostMapping(value = "/addAll", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Country>> postBulk(@RequestBody List<Country> countries) {
        List<Country> old = countryService.addAll(countries);
        if (old.isEmpty()) {
            return ResponseEntity
                    .created(URI.create("/countries"))
                    .body(countries);
        } else {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .header("Content-Location", "/countries")
                    .body(old);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Country> add(@RequestBody Country country) throws URISyntaxException {
        Country c = countryService.add(country);
        if(c == null){
            return ResponseEntity
                    .created(new URI("/countries/" + country.getId()))
                    .body(country);
        }
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .header("Content-Location", "/countries/" + c.getId())
                .body(c);
    }

    @PostMapping(value = "/trip/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Trip> addTrip(@PathVariable int id, @RequestBody Trip trip) throws URISyntaxException, TripNotFoundException {
        Country t = countryService.get(id);
        if(!countryService.addTrip(trip, id)){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void remove(@PathVariable int id){
        countryService.remove(id);
    }

        @DeleteMapping(value = "/trip/{countryId}/{tripId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> removeTrip(@PathVariable int countryId, @PathVariable int tripId){
        if(countryService.removeTrip(countryId, tripId)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}

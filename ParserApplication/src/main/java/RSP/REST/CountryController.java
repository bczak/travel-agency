package RSP.REST;

import RSP.model.Country;
import RSP.model.Tag;
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
        countryService.add(country);
        return ResponseEntity
                .created(new URI("/countries/" + country.getId()))
                .body(country);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void remove(@PathVariable int id) throws TripNotFoundException {
        countryService.remove(id);
    }
}

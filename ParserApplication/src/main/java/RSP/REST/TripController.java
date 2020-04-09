package RSP.REST;

import RSP.dto.SortAttribute;
import RSP.dto.SortOrder;
import RSP.dto.TripsQueryCriteria;
import RSP.model.Trip;
import RSP.service.TripNotFoundException;
import RSP.service.TripService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/trips")
public class TripController {
    TripService tripService;

    TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Trip> getAll() {
        return tripService.getAll();
    }

    @GetMapping(value = "/sort", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Trip> getAllSorted(@RequestParam SortAttribute by, @RequestParam SortOrder order) {
        return tripService.getAllSorted(by, order);
    }

    @GetMapping(value = "/query", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Trip> getAll(TripsQueryCriteria criteria) {
        return tripService.getAllSorted(criteria.getSortBy(), criteria.getOrder());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Trip get(@PathVariable int id) throws TripNotFoundException {
        return tripService.get(id);
    }

    @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    Trip get(@PathVariable String name) throws TripNotFoundException {
        return tripService.getByName(name);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Trip> add(@RequestBody Trip trip) throws URISyntaxException {
        Trip old = tripService.add(trip);
        if (old != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).header("Content-Location", "/trips/" + old.getId()).body(old);
        } else {
            String myUrl = "/trips/" + trip.getId();
            URI myURI = new URI(myUrl);
            return ResponseEntity.created(myURI).body(trip);
        }
    }

    //DELETE REQUESTS
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    void remove(@PathVariable int id) throws TripNotFoundException {
        tripService.remove(id);
    }

    @ExceptionHandler(TripNotFoundException.class)
    void handleTripNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }
}

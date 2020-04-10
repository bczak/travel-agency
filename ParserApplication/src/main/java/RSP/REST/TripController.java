package RSP.REST;

import RSP.dto.SortAttribute;
import RSP.dto.SortOrder;
import RSP.dto.TripsQueryCriteria;
import RSP.model.Trip;
import RSP.service.InconsistentQueryException;
import RSP.service.InvalidQueryException;
import RSP.service.TripNotFoundException;
import RSP.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/trips")
public class TripController {

    TripService tripService;

    private final static Logger log = Logger.getLogger(TripController.class.getName());

    TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Trip> getAll() {
        log.info("path: /trips GET method getAll is invoked");
        return tripService.getAll();
    }

    @GetMapping(value = "/sort", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Trip> getAllSorted(@RequestParam SortAttribute by, @RequestParam SortOrder order) {
        log.info("path: /trips/sort GET method getAllSorted is invoked by " + by + " order " + order);
        return tripService.getAllSorted(by, order);
    }

    @GetMapping(value = "/query", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Trip> getAll(TripsQueryCriteria criteria)
            throws InvalidQueryException, InconsistentQueryException {
        return tripService.getSome(criteria);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Trip get(@PathVariable int id) throws TripNotFoundException {
        log.info("path: /trips/{id} GET method get is invoked where id = " + id);
        return tripService.get(id);
    }

    @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    Trip get(@PathVariable String name) throws TripNotFoundException {
        log.info("path: /trips/name/{name} GET method get is invoked where name = " + name);
        return tripService.getByName(name);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Trip> add(@RequestBody Trip trip) throws URISyntaxException {
        log.info("path: /trips POST method add is invoked");
        Trip old = tripService.add(trip);
        if (old != null) {
            log.info("path: /trips POST method add is invoked with error CONFLICT");
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .header("Content-Location", "/trips/" + old.getId())
                    .body(old);
        } else {
            return ResponseEntity
                    .created(new URI("/trips/" + trip.getId()))
                    .body(trip);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    void remove(@PathVariable int id) throws TripNotFoundException {
        log.info("path: /trips DELETE method remove is invoked with id = " + id);
        tripService.remove(id);
    }

    @ExceptionHandler(TripNotFoundException.class)
    void handleTripNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler({InconsistentQueryException.class, InvalidQueryException.class})
    void handleInvalidQuery(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }
}

package RSP.REST;

import RSP.dto.SortAttribute;
import RSP.dto.SortOrder;
import RSP.dto.TripsQueryCriteria;
import RSP.model.Country;
import RSP.model.Tag;
import RSP.model.Trip;
import RSP.model.User;
import RSP.service.*;
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
@RequestMapping("/api/trips")
public class TripController {

    TripService tripService;
    TagService tagService;

    private final static Logger log = Logger.getLogger(TripController.class.getName());

    TripController(TripService tripService, TagService tagService) {
        this.tripService = tripService;
        this.tagService = tagService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Trip> getAll(TripsQueryCriteria criteria)
            throws InvalidQueryException, InconsistentQueryException {
        log.info(() -> "REST GET /trips invoked with " + criteria);
        List<Trip> results = tripService.getSome(criteria);
        log.info(() -> "REST GET /trips returned OK with " + results.size() + " trips");
        return results;
    }

    @GetMapping(value = "/sort", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Trip> getAllSorted(@RequestParam SortAttribute by, @RequestParam SortOrder order) {
        log.info("path: /trips/sort GET method getAllSorted is invoked by " + by + " order " + order);
        return tripService.getAllSorted(by, order);
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

    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<User> getUsersOfTrip(@PathVariable int id) throws TripNotFoundException {
        log.info("path: /trips/users/{id} GET method getUsersOfTrip is invoked where tasakId = " + id);
        return tripService.getUsersFromTrip(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Trip> add(@RequestBody Trip trip) throws URISyntaxException {
        log.info("path: /trips POST method add is invoked");
        Trip old = tripService.add(trip);
        if (old == null) {
            return ResponseEntity
                    .created(new URI("/trips/" + trip.getId()))
                    .body(trip);
        } else {
            log.info("path: /trips POST method add is invoked with error CONFLICT");
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .header("Content-Location", "/trips/" + old.getId())
                    .body(old);
        }
    }

    @PostMapping(value = "/tags/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Trip> addTags(@PathVariable int id, @RequestBody Tag tag) throws URISyntaxException, TripNotFoundException {
        log.info("path: /trips/tags/{TripId} POST method addTags is invoked where tripId = " + id);
        Trip t = tripService.get(id);
        if(!tripService.addTags(tag, id)){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/country/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Trip> addCountry(@PathVariable int id, @RequestBody Country country) throws URISyntaxException, TripNotFoundException {
        log.info("path: /trips/country/{TripId} POST method addCountry is invoked where tripId = " + id);
        Trip t = tripService.get(id);
        if(!tripService.addCountry(country, id)){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    void remove(@PathVariable int id) throws TripNotFoundException {
        log.info("path: /trips DELETE method remove is invoked with id = " + id);
        tripService.remove(id);
    }

    @DeleteMapping(value = "/tags/{tripId}/{tagId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> removeTag(@PathVariable int tripId, @PathVariable int tagId) throws TripNotFoundException {
        log.info("path: /trips/tags/{tripId}/{tagId} DELETE method removeTag is invoked with tagId = " + tagId);
        if(tripService.removeTag(tagId, tripId)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping(value = "/country/{tripId}/{countryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> removeCountry(@PathVariable int tripId, @PathVariable int countryId) throws TripNotFoundException {
        log.info("path: /trips/country/{tripId}/{countryId} DELETE method removeCountry is invoked with countryId = " + countryId);
        if(tripService.removeCountry(tripId, countryId)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    // BULK OPERATIONS

    /**
     * Returns an array with all trips in database.
     */
    @GetMapping(value = "/bulk", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Trip> getBulk() {
        log.info("REST GET /trips/bulk invoked");
        List<Trip> result = tripService.getAll();
        log.info(() -> "REST GET /trips/bulk returned OK with " + result.size() + " trips");
        return result;
    }

    /**
     * Adds all trips from array into database.
     *
     * @param trips array of trips without IDs
     * @return OK with an array of added trips including their assigned IDs
     *         or CONFLICT with an array of existing trips with conflicting names.
     */
    @PostMapping(value = "/bulk", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Trip>> postBulk(@RequestBody List<Trip> trips) {
        log.info(() -> "REST POST /trips/bulk invoked with " + trips.size() + " trips");
        List<Trip> old = tripService.addAll(trips);
        if (old.isEmpty()) {
            log.info(() ->
                    "REST POST /trips/bulk returned OK with " + trips.size() + " trips");
            return ResponseEntity
                    .created(URI.create("/trips"))
                    .body(trips);
        } else {
            log.info(() ->
                    "REST POST /trips/bulk returned CONFLICT with " + old.size() + "trips");
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .header("Content-Location", "/trips")
                    .body(old);
        }
    }

    /**
     * Replaces content of database with trips from array.
     *
     * @param trips array of trips without IDs
     * @return OK with an array of all trips including their assigned IDs
     *         or CONFLICT with an array of requested trips that could not be added.
     */
    @PutMapping(value = "/bulk", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Trip>> putBulk(@RequestBody List<Trip> trips) {
        log.info(() -> "REST PUT /trips/bulk invoked with " + trips.size() + " trips");
        List<Trip> bad = tripService.setAll(trips);
        if (bad.isEmpty()) {
            log.info(() -> "REST PUT /trips/bulk returned OK with " + trips.size() + " trips");
            return ResponseEntity
                    .created(URI.create("/trips"))
                    .body(trips);
        } else {
            log.info(() ->
                    "REST PUT /trips/bulk returned CONFLICT with " + bad.size() + " trips");
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(bad);
        }
    }

    /**
     * Update content of database with trips from array.
     * Trips from array replace existing trips with same name or are added into database
     * if database does not contain trip with such name.
     *
     * @param trips array of trips without IDs
     * @return OK with an array of added or updated trips with IDs
     */
    @PatchMapping(value = "/bulk", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Trip> patchBulk(@RequestBody List<Trip> trips) {
        log.info(() -> "REST PATCH /trips/bulk invoked with " + trips.size() + " trips");
        List<Trip> result = tripService.updateAll(trips);
        log.info(() -> "REST PATCH /trips/bulk returned OK with " + result.size() + " trips");
        return result;
    }

    /**
     * Delete database of trips.
     */
    @DeleteMapping("/bulk")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteBulk() {
        log.info("REST DELETE /trips/bulk invoked");
        tripService.removeAll();
        log.info("REST DELETE /trips/bulk returned NO_CONTENT");
    }

    // EXCEPTIONS

    @ExceptionHandler(TripNotFoundException.class)
    void handleTripNotFound(HttpServletResponse response, Exception exception)
            throws IOException {
        log.info(() -> "REST returned NOT_FOUND with error: " + exception.getMessage());
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler({InconsistentQueryException.class, InvalidQueryException.class})
    void handleInvalidQuery(HttpServletResponse response, Exception exception)
            throws IOException {
        log.info("REST returned UNPROCESSABLE_ENTITY with error: " + exception.getMessage());
        response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }
}

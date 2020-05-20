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
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    TripService tripService;
    TagService tagService;

    private static final Logger log = Logger.getLogger(TripController.class.getName());

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

    @Deprecated
    @GetMapping(value = "/sort", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Trip> getAllSorted(@RequestParam SortAttribute by, @RequestParam SortOrder order) {
        log.info(() -> "REST GET /trips/sort invoked with attribute = "
                + by + " and order = " + order);
        List<Trip> result = tripService.getAllSorted(by, order);
        log.info(() -> "REST GET /trips/sort returned OK with " + result.size() + " trips");
        return result;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Trip get(@PathVariable int id) throws TripNotFoundException {
        log.info(() -> "REST GET /trips/{id} invoked with id = " + id);
        Trip result = tripService.get(id);
        log.info("REST GET /trips/{id} returned OK");
        return result;
    }

    @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    Trip get(@PathVariable String name) throws TripNotFoundException {
        log.info(() -> "REST GET /trips/name/{name} invoked with name = " + name);
        Trip result = tripService.getByName(name);
        log.info("REST GET /trips/name/{name} returned OK");
        return result;
    }

    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<User> getUsersOfTrip(@PathVariable int id) throws TripNotFoundException {
        log.info(() -> "REST GET /trips/users/{id} invoked with id = " + id);
        List<User> result = tripService.getUsersFromTrip(id);
        log.info(() -> "REST GET /trips/users/{id} returned OK with "
                + result.size() + " users");
        return result;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Trip> add(@Valid @RequestBody Trip trip) {
        log.info("REST POST /trips invoked");
        Trip old = tripService.add(trip);
        if (old == null) {
            log.info("REST POST /trips returned CREATED");
            return ResponseEntity
                    .created(URI.create("/trips/" + trip.getId()))
                    .body(trip);
        } else {
            log.info("REST POST /trips returned CONFLICT");
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .header("Content-Location", "/trips/" + old.getId())
                    .body(old);
        }
    }

    @PostMapping(value = "/tags/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Trip> addTags(@PathVariable int id, @RequestBody Tag tag)
            throws TripNotFoundException {
        log.info(() -> "REST POST /trips/tags/{tripId} invoked with tripId = " + id);
        tripService.get(id);
        if (!tripService.addTags(tag, id)) {
            log.info("REST POST /trips/tags/{tripId} returned CONFLICT");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            log.info("REST POST /trips/tags/{tripId} returned CREATED");
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @PostMapping(value = "/country/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Trip> addCountry(@PathVariable int id, @RequestBody Country country)
            throws TripNotFoundException {
        log.info(() -> "REST POST /trips/country/{tripId} invoked with tripId = " + id);
        tripService.get(id);
        if (!tripService.addCountry(country, id)) {
            log.info("REST POST /trips/country/{tripId} returned CONFLICT");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            log.info("REST POST /trips/country/{tripID} returned CREATED");
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    void remove(@PathVariable int id) throws TripNotFoundException {
        log.info(() -> "REST DELETE /trips/{id} invoked with id = " + id);
        tripService.remove(id);
        log.info("REST DELETE /trips/{id} returned NO_CONTENT");
    }

    @DeleteMapping(value = "/tags/{tripId}/{tagId}")
    ResponseEntity<Void> removeTag(@PathVariable int tripId, @PathVariable int tagId)
            throws TripNotFoundException {
        log.info(() -> "REST DELETE /trips/tags/{tripId}/{tagId} invoked with tripId = "
                + tripId + " and tagId = " + tagId);
        if (tripService.removeTag(tagId, tripId)) {
            log.info("REST DELETE /trips/tags/{tripId}/{tagId} returned NO_CONTENT");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            log.info("REST DELETE /trips/tags/{tripId}/{tagId} returned FORBIDDEN");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping(value = "/country/{tripId}/{countryId}")
    ResponseEntity<Void> removeCountry(@PathVariable int tripId, @PathVariable int countryId)
            throws TripNotFoundException {
        log.info(() -> "REST DELETE /trips/country/{tripId}/{countryId} invoked with tripId = "
                + tripId + " and countryId = " + countryId);
        if (tripService.removeCountry(tripId, countryId)) {
            log.info("REST DELETE /trips/country/{tripId}/{countryId} returned NO_CONTENT");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            log.info("REST DELETE /trips/country/{tripOd}/{countryId} returned FORBIDDEN");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
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
        log.info(() -> "REST /trips... returned NOT_FOUND with error: "
                + exception.getMessage());
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler({InconsistentQueryException.class, InvalidQueryException.class})
    void handleInvalidQuery(HttpServletResponse response, Exception exception)
            throws IOException {
        log.info(() -> "REST /trips... returned UNPROCESSABLE_ENTITY with error: "
                + exception.getMessage());
        response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }
}

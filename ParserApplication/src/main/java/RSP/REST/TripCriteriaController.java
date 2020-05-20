package RSP.REST;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import RSP.model.Trip;
import RSP.model.TripCriteria;
import RSP.service.InconsistentQueryException;
import RSP.service.InvalidQueryException;
import RSP.service.TripCriteriaNotFoundException;
import RSP.service.TripCriteriaService;

@RestController
@RequestMapping("/api/criterias")
public class TripCriteriaController {

    private final TripCriteriaService criteriaService;

    private static final Logger log = Logger.getLogger(TripCriteriaController.class.getName());

    TripCriteriaController(TripCriteriaService criteriaService) {
        this.criteriaService = criteriaService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<TripCriteria> getAll() {
        log.info("REST GET /criterias invoked");
        List<TripCriteria> results = criteriaService.getAll();
        log.info(() -> "REST GET /criterias returned OK with " + results.size() + " criterias");
        return results;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    TripCriteria get(@PathVariable int id) throws TripCriteriaNotFoundException {
        log.info(() -> "REST GET /criterias/{id} invoked with id = " + id);
        TripCriteria result = criteriaService.get(id);
        log.info("REST GET /criterias/{id} returned OK");
        return result;
    }

    @GetMapping(value = "/{id}/trips", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Trip> getTrips(@PathVariable int id)
            throws TripCriteriaNotFoundException,
                    InvalidQueryException,
                    InconsistentQueryException {
        log.info(() -> "REST GET /criterias/{id}/trips invoked with id = " + id);
        List<Trip> result = criteriaService.getSelectedTrips(id);
        log.info(() ->
                "REST GET /criterias/{id}/trips returned with " + result.size() + " trips");
        return result;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<TripCriteria> add(@RequestBody TripCriteria criteria)
            throws InvalidQueryException, InconsistentQueryException {
        log.info("REST POST /criterias invoked");
        criteriaService.add(criteria);
        log.info("REST POST /criterias returned CREATED");
        return ResponseEntity
                .created(URI.create("/api/criterias/" + criteria.getId()))
                .body(criteria);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void remove(@PathVariable int id) throws TripCriteriaNotFoundException {
        log.info(() -> "REST DELETE /criterias/{id} invoked with id = " + id);
        criteriaService.remove(id);
        log.info("REST DELETE /criterias/{id} returned NO_CONTENT");
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeAll() {
        log.info("REST DELETE /criterias invoked");
        criteriaService.removeAll();
        log.info("REST DELETE /criterias returned NO_CONTENT");
    }

    @ExceptionHandler({
        TripCriteriaNotFoundException.class,
        InvalidQueryException.class,
        InconsistentQueryException.class})
    void handleTripCriteriaNotFound(HttpServletResponse response, Exception exception)
            throws IOException {
        log.info(() ->
                "REST /criterias... returned NOT_FOUND wuth error: " + exception.getMessage());
        response.sendError(HttpStatus.NOT_FOUND.value());
    }
}

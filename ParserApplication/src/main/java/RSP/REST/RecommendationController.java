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

import RSP.model.Recommendation;
import RSP.service.RecommendationNotFoundException;
import RSP.service.RecommendationService;
import RSP.service.TripCriteriaNotFoundException;
import RSP.service.TripNotFoundException;


@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    RecommendationService recommendationService;

    private static final Logger log
            = Logger.getLogger(RecommendationController.class.getName());

    RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Recommendation> getAll() {
        log.info("REST GET /recommendations invoked");
        List<Recommendation> result = recommendationService.getAll();
        log.info(() -> "REST GET /recommendations returned OK with "
                + result.size() + " recommendations");
        return result;
    }

    @GetMapping(value = "/undelivered", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Recommendation> getUndelivered() {
        log.info("REST GET /recommendations/undelivered invoked");
        List<Recommendation> result = recommendationService.getUndelivered();
        log.info(() -> "REST GET /recommendations/undelivered returned OK with "
                + result.size() + " recommendations");
        return result;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Recommendation get(@PathVariable int id) throws RecommendationNotFoundException {
        log.info(() -> "REST GET /recommendations/{id} invoked with id = " + id);
        Recommendation recommendation = recommendationService.get(id);
        log.info("REST GET /recommendations/{id} returned OK");
        return recommendation;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Recommendation> add(@RequestBody Recommendation recommendation)
            throws TripNotFoundException, TripCriteriaNotFoundException {
        log.info("REST POST /recommendations invoked");
        recommendationService.add(recommendation);
        int id = recommendation.getId();
        log.info(() -> "REST POST /recommendations returned OK with id = " + id);
        return ResponseEntity
                .created(URI.create("/recommendations/" + id))
                .body(recommendation);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void remove(@PathVariable int id) throws RecommendationNotFoundException {
        log.info(() -> "REST DELETE /recommendations/{id} invoked with id = " + id);
        recommendationService.remove(id);
        log.info("REST DELETE /recommendations/{id} returned NO_CONTENT");
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeAll() {
        log.info("REST DELETE /recommendations invoked");
        recommendationService.removeAll();
        log.info("REST DELETE /recommendations returned NO_CONTENT");
    }

    @DeleteMapping(value = "/{id}/undelivered")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void markDelivered(@PathVariable int id) throws RecommendationNotFoundException {
        log.info(() -> "REST DELETE /recommendations/{id}/undelivered invoked with id = " + id);
        recommendationService.markDelivered(id);
        log.info("REST DELETE /recommendations/{id}/undelivered returned NO_CONTENT");
    }

    @ExceptionHandler({
        RecommendationNotFoundException.class,
        TripNotFoundException.class,
        TripCriteriaNotFoundException.class})
    void handleRecommendationNotFoundException(
            HttpServletResponse response, Exception exception) throws IOException {
        log.info("REST returned NOT_FOUND with error: " + exception.getMessage());
        response.sendError(HttpStatus.NOT_FOUND.value());
    }
}

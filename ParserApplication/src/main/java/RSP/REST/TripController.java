package RSP.REST;

import RSP.model.Trip;
import RSP.service.TripNotFoundException;
import RSP.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Trip get(@PathVariable int id) throws TripNotFoundException {
        return tripService.get(id);
    }

    @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    Trip get(@PathVariable String name) throws TripNotFoundException {
        return tripService.getByName(name);
    }

    @GetMapping(value = "/priceASC",produces = MediaType.APPLICATION_JSON_VALUE)
    List<Trip> get() throws TripNotFoundException
    {
        return tripService.getByPriceASC();
    }

    @GetMapping(value = "/date",produces = MediaType.APPLICATION_JSON_VALUE)
    List<Trip> getByStartDate() throws TripNotFoundException
    {
        return tripService.getByStartDate();
    }

    @GetMapping(value = "/length",produces = MediaType.APPLICATION_JSON_VALUE)
    List<Trip> getByLength() throws TripNotFoundException
    {
        return tripService.getByLength();
    }

    @PostMapping
    ResponseEntity<Void> add(@RequestBody Trip trip) {
        if(tripService.add(trip))
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }

    //DELETE REQUESTS
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    void remove(@PathVariable int id) throws TripNotFoundException {
        tripService.remove(id);
    }
}

package RSP.REST;

import RSP.model.Trip;
import RSP.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController {
    TripService tripService;

    TripController(TripService tripService)
    {
        this.tripService = tripService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Trip> getAll()
    {
        return tripService.getAll();
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    Trip get(@PathVariable int id)
    {
        return tripService.get(id);
    }

    @GetMapping(value = "/name/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    Trip get(@PathVariable String name)
    {
        return tripService.getByName(name);
    }

    @PostMapping
    ResponseEntity<Void> add(@RequestBody Trip trip)
    {
        if(tripService.add(trip))
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }

    //DELETE REQUESTS
    @DeleteMapping(value = "/{id}")
    //@PreAuthorize("hasRole('Admin')")
    ResponseEntity<Void> remove(@PathVariable int id)
    {
        if(tripService.remove(id))
            return new ResponseEntity<Void>(HttpStatus.OK);
        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }
}

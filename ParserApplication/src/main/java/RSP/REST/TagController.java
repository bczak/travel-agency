package RSP.REST;

import RSP.dto.TripsQueryCriteria;
import RSP.model.Tag;
import RSP.model.Trip;
import RSP.service.TagService;
import RSP.service.TripNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Tag> getAll(){
        return tagService.getAll();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Tag> add(@RequestBody Tag tag) throws URISyntaxException {
        tagService.add(tag);
        return ResponseEntity
                .created(new URI("/tags/" + tag.getId()))
                .body(tag);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void remove(@PathVariable int id) throws TripNotFoundException {
        tagService.remove(id);
    }
}

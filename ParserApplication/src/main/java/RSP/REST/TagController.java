package RSP.REST;

import RSP.model.Tag;
import RSP.model.Trip;
import RSP.service.TagService;
import RSP.service.TripNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping(value = "/getByName/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Tag> get(@PathVariable String name){
        Tag t = tagService.getByName(name);
        if(t == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(t, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Tag> getById(@PathVariable int id){
        Tag t = tagService.get(id);
        if(t == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(t,HttpStatus.OK);
    }

    @PostMapping(value = "/addAll", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Tag>> postBulk(@Valid @RequestBody List<Tag> tags) {
        List<Tag> old = tagService.addAll(tags);
        if (old.isEmpty()) {
            return ResponseEntity
                    .created(URI.create("/tags"))
                    .body(tags);
        } else {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .header("Content-Location", "/tags")
                    .body(old);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Tag> add(@Valid @RequestBody Tag tag) throws URISyntaxException {
        Tag t = tagService.add(tag);
        if(t == null){
            return ResponseEntity
                    .created(new URI("/tags/" + tag.getId()))
                    .body(tag);
        }
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .header("Content-Location", "/tags/" + t.getId())
                .body(t);

    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<?> remove(@PathVariable int id) throws TripNotFoundException {
        if(tagService.get(id) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        tagService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

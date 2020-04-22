package RSP.REST;

import RSP.model.Tag;
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

    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    Tag get(@PathVariable String name){
        return tagService.getByName(name);
    }

    @PostMapping(value = "/addAll", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Tag>> postBulk(@RequestBody List<Tag> tags) {
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
    ResponseEntity<Tag> add(@RequestBody Tag tag) throws URISyntaxException {
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void remove(@PathVariable int id) throws TripNotFoundException {
        tagService.remove(id);
    }
}

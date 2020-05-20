package RSP.REST;

import RSP.model.Tag;
import RSP.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    TagService tagService;

    private static final Logger log = Logger.getLogger(TagController.class.getName());

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Tag> getAll() {
        log.info("REST GET /tags invoked");
        List<Tag> result = tagService.getAll();
        log.info(() -> "REST GET /tags returned OK with " + result.size() + " tags");
        return result;
    }

    @GetMapping(value = "/getByName/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Tag> get(@PathVariable String name) {
        log.info(() -> "REST GET /tags/getByName/{name} invoked with name = " + name);
        Tag t = tagService.getByName(name);
        if (t == null) {
            log.info("REST GET /tags/getByName/{name} returned NOT_FOUND");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            log.info("REST GET /tags/getByName/{name} returned OK");
            return new ResponseEntity<>(t, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Tag> getById(@PathVariable int id) {
        log.info(() -> "REST GET /tags/{id} invoked with id = " + id);
        Tag t = tagService.get(id);
        if (t == null) {
            log.info("REST GET /tags/{id} returned NOT_FOUND");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            log.info("REST GET /tags/{id} returned OK");
            return new ResponseEntity<>(t,HttpStatus.OK);
        }
    }

    @PostMapping(value = "/addAll", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Tag>> postBulk(@Valid @RequestBody List<Tag> tags) {
        log.info(() -> "REST POST /tags/addAll invoked with " + tags.size() + " tags");
        List<Tag> old = tagService.addAll(tags);
        if (old.isEmpty()) {
            log.info(() -> "REST POST /tags/addAll returned CREATED with "
                    + tags.size() + "tags");
            return ResponseEntity
                    .created(URI.create("/tags"))
                    .body(tags);
        } else {
            log.info(() -> "REST POST /tags/addAll returned CONFLICT with "
                    + old.size() + " tags");
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .header("Content-Location", "/tags")
                    .body(old);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Tag> add(@Valid @RequestBody Tag tag) {
        log.info("REST POST /tags invoked");
        Tag t = tagService.add(tag);
        if (t == null) {
            log.info("REST POST /tags returned CREATED");
            return ResponseEntity
                    .created(URI.create("/tags/" + tag.getId()))
                    .body(tag);
        } else {
            log.info("REST POST /tags returned CONFLICT");
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .header("Content-Location", "/tags/" + t.getId())
                    .body(t);
        }
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<?> remove(@PathVariable int id) {
        log.info(() -> "REST DELETE /tags/{id} with id = " + id);
        if (tagService.get(id) == null) {
            log.info("REST DELETE /tags/{id} returned NOT_FOUND");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            tagService.remove(id);
            log.info("REST DELETE /tags/{id} returned NO_CONTENT");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}

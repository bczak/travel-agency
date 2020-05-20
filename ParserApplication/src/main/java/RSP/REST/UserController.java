package RSP.REST;

import RSP.model.User;
import RSP.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/user")
public class UserController {

    UserService userService;

    private static final Logger log = Logger.getLogger(UserController.class.getName());

    UserController(UserService userService) {
        this.userService = userService;
    }

    //GET REQUESTS
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<User> getAll() {
        log.info("REST GET /user invoked");
        List<User> result = userService.getAll();
        log.info(() -> "REST GET /user returned OK with " + result.size() + " users");
        return result;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    User get(@PathVariable int id) {
        log.info(() -> "REST GET /user/{id} invoked with id = " + id);
        User result = userService.get(id);
        log.info("REST GET /user/{id} returned OK");
        return result;
    }

    @GetMapping(value = "/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    User get(@PathVariable String username) {
        log.info(() ->
                "REST GET /user/username/{username} invoked with username = " + username);
        User result = userService.getByUsername(username);
        log.info("REST GET /user/username/{username} returned OK");
        return result;
    }

    //POST REQUESTS
    @PostMapping(value = "/add")
    ResponseEntity<Void> add(@RequestBody User user) {
        log.info("REST POST /user/add invoked");
        if (userService.add(user)) {
            log.info("REST POST /user/add returned CREATED");
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } else {
            log.info("REST POST /user/add returned CONFLICT");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
    }

    //DELETE REQUESTS
    @DeleteMapping(value = "/remove/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN)")
    ResponseEntity<Void> remove(@PathVariable int id) {
        log.info(() -> "REST DELETE /user/remove/{id} invoked with id = " + id);
        if (userService.remove(id)) {
            log.info("REST DELETE /user/remove/{id} returned OK");
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            log.info("REST DELETE /user/remove/{id} returned NOT_FOUND");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }
}

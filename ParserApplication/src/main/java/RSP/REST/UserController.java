package RSP.REST;

import RSP.model.User;
import RSP.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    //GET REQUESTS
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    User get(@PathVariable int id) {
        return userService.get(id);
    }

    @GetMapping(value = "/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    User get(@PathVariable String username) {
        return userService.getByUsername(username);
    }

    //POST REQUESTS
    @PostMapping(value = "/add")
    ResponseEntity<Void> add(@RequestBody User user) {
        if(userService.add(user))
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }

    //DELETE REQUESTS
    @DeleteMapping(value = "/remove/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN)")
    ResponseEntity<Void> remove(@PathVariable int id) {
        if(userService.remove(id))
            return new ResponseEntity<Void>(HttpStatus.OK);
        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }
}

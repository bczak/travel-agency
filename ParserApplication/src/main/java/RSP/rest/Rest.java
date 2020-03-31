package RSP.rest;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class Rest
{
//    UserService userService;
//
//    Rest(UserService userService)
//    {
//        this.userService = userService;
//    }
//
//    //GET REQUESTS
//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    List<User> getAll()
//    {
//        return userService.getAll();
//    }
//
//    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
//    User get(@PathVariable int id)
//    {
//        return userService.get(id);
//    }
//
//    @GetMapping(value = "/username/{username}",produces = MediaType.APPLICATION_JSON_VALUE)
//    User get(@PathVariable String username)
//    {
//        return userService.getByUsername(username);
//    }
//
//    //POST REQUESTS
//    @PostMapping(value = "/add")
//    ResponseEntity<Void> add(@RequestBody User user)
//    {
//        if(userService.add(user))
//            return new ResponseEntity<Void>(HttpStatus.CREATED);
//        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
//    }
//
//    //DELETE REQUESTS
//    @DeleteMapping(value = "/remove/{id}")
//    //@PreAuthorize("hasRole('ROLE_ADMIN')")
//    ResponseEntity<Void> remove(@PathVariable int id)
//    {
//        if(userService.remove(id)) //also remove cart ?
//            return new ResponseEntity<Void>(HttpStatus.OK);
//        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
//    }
}

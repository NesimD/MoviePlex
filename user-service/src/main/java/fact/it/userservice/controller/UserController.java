package fact.it.userservice.controller;

import fact.it.userservice.dto.MovieRequest;
import fact.it.userservice.dto.SerieRequest;
import fact.it.userservice.dto.UserRequest;
import fact.it.userservice.dto.UserResponse;
import fact.it.userservice.model.User;
import fact.it.userservice.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable long id) {
        Optional<UserResponse> userOptional = userService.getUserById(id);

        if (userOptional.isPresent()) {
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
       UserResponse userResponse = userService.createUser(userRequest);

       if (userResponse == null) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
       return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping("{id}/favoriteMovie")
    public ResponseEntity<UserResponse> addFavoriteMovie(@PathVariable long id, @RequestBody MovieRequest movieRequest) {
        UserResponse userResponse = userService.addFavoriteMovie(id, movieRequest);

        if (userResponse == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping("{id}/favoriteSerie")
    public ResponseEntity<UserResponse> addFavoriteSerie(@PathVariable long id, @RequestBody SerieRequest serieRequest) {
       UserResponse userResponse = userService.addFavoriteSerie(id, serieRequest);

       if (userResponse == null) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
       return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/{id}/favoriteMovie/{mediaCode}")
    @ResponseStatus(HttpStatus.OK)
    public void removeFavoriteMovie(@PathVariable long id, @PathVariable String mediaCode) {
        userService.removeFavoriteMovie(id, mediaCode);
    }

    @Transactional
    @DeleteMapping("/{id}/favoriteSerie/{mediaCode}")
    @ResponseStatus(HttpStatus.OK)
    public void removeFavoriteSerie(@PathVariable long id, @PathVariable String mediaCode) {
        userService.removeFavoriteSerie(id, mediaCode);
    }
}

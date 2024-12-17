package fact.it.userservice.controller;

import fact.it.userservice.dto.MovieRequest;
import fact.it.userservice.dto.SerieRequest;
import fact.it.userservice.dto.UserRequest;
import fact.it.userservice.dto.UserResponse;
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
    @ResponseStatus(HttpStatus.OK)
    public void createUser(@RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
    }

    @PostMapping("{id}/favoriteMovie")
    @ResponseStatus(HttpStatus.OK)
    public void addFavoriteMovie(@PathVariable long id, @RequestBody MovieRequest movieRequest) {
        userService.addFavoriteMovie(id, movieRequest);
    }

    @PostMapping("{id}/favoriteSerie")
    @ResponseStatus(HttpStatus.OK)
    public void addFavoriteSerie(@PathVariable long id, @RequestBody SerieRequest serieRequest) {
        userService.addFavoriteSerie(id, serieRequest);
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

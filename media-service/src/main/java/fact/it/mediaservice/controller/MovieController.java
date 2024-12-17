package fact.it.mediaservice.controller;

import fact.it.mediaservice.dto.MovieRequest;
import fact.it.mediaservice.dto.MovieResponse;
import fact.it.mediaservice.model.Movie;
import fact.it.mediaservice.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/media/movie")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping("{mediaCode}")
    @ResponseStatus(HttpStatus.OK)
    public MovieResponse getMovieByMediaCode(@PathVariable("mediaCode") String mediaCode) {
        return movieService.getMovieByMediaCode(mediaCode);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MovieResponse> getMoviesByMediaCode(@RequestParam List<String> mediaCodes) {
        return movieService.getMoviesByMediaCode(mediaCodes);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<MovieResponse> getAllMovies() {
        return movieService.getAllMovies();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createMovie(@RequestBody MovieRequest movieRequest) {
        movieService.createMovie(movieRequest);
    }

    @PutMapping("{id}")
    public ResponseEntity<MovieResponse> updateMovie(@RequestBody MovieRequest movieRequest, @PathVariable("id") String id) {
        MovieResponse movieResponse = movieService.updateMovieById(movieRequest, id);

        if (movieResponse == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(movieResponse, HttpStatus.OK);
    }
}

package fact.it.mediaservice.controller;

import fact.it.mediaservice.dto.movie.MovieRequest;
import fact.it.mediaservice.dto.movie.MovieResponse;
import fact.it.mediaservice.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<MovieResponse> createMovie(@RequestBody MovieRequest movieRequest) {
        MovieResponse movieResponse = movieService.createMovie(movieRequest);

        if (movieResponse == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @PutMapping("{mediaCode}")
    public ResponseEntity<MovieResponse> updateMovie(@RequestBody MovieRequest movieRequest, @PathVariable("mediaCode") String mediaCode) {
        MovieResponse movieResponse = movieService.updateMovieByMediaCode(movieRequest, mediaCode);

        if (movieResponse == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(movieResponse, HttpStatus.OK);
    }
}

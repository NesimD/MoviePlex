package fact.it.mediaservice.controller;

import fact.it.mediaservice.dto.genre.GenreRequest;
import fact.it.mediaservice.dto.genre.GenreResponse;
import fact.it.mediaservice.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/media/genre")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping("{id}")
    public ResponseEntity<GenreResponse> getGenreById(@PathVariable("id") String id) {
        Optional<GenreResponse> genreResponseOptional = genreService.getGenreById(id);

        if (genreResponseOptional.isPresent()) {
            GenreResponse genreResponse = genreResponseOptional.get();

            return new ResponseEntity<>(genreResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<GenreResponse> getAllGenres() {
        return genreService.getAllGenres();
    }

    @PostMapping
    public ResponseEntity<GenreResponse> createGenre(@RequestBody GenreRequest genreRequest) {
        GenreResponse genreResponse = genreService.createGenre(genreRequest);

        if (genreResponse == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(genreResponse, HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGenre(@PathVariable String id) {
        genreService.deleteGenre(id);
    }
}

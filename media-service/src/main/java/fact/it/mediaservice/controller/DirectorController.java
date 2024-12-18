package fact.it.mediaservice.controller;

import fact.it.mediaservice.dto.director.DirectorRequest;
import fact.it.mediaservice.dto.director.DirectorResponse;
import fact.it.mediaservice.service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/media/director")
@RequiredArgsConstructor
public class DirectorController {
    private final DirectorService directorService;

    @GetMapping("{id}")
    public ResponseEntity<DirectorResponse> getDirectorById(@PathVariable("id") String id) {
        Optional<DirectorResponse> directorResponseOptional = directorService.getDirectorById(id);

        if (directorResponseOptional.isPresent()) {
            DirectorResponse directorResponse = directorResponseOptional.get();

            return new ResponseEntity<>(directorResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<DirectorResponse> getAllDirector() {
        return directorService.getAllDirectors();
    }

    @PostMapping
    public ResponseEntity<DirectorResponse> createDirector(@RequestBody DirectorRequest directorRequest) {
        DirectorResponse directorResponse =  directorService.createDirector(directorRequest);

        if (directorResponse == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(directorResponse, HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDirector(@PathVariable String id) {
        directorService.deleteDirector(id);
    }
}

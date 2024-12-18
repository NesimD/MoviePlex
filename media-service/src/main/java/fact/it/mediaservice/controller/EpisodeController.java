package fact.it.mediaservice.controller;

import fact.it.mediaservice.dto.episode.EpisodeRequest;
import fact.it.mediaservice.dto.episode.EpisodeResponse;
import fact.it.mediaservice.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/media/episode")
@RequiredArgsConstructor
public class EpisodeController {
    private final EpisodeService episodeService;

    @GetMapping("{id}")
    public ResponseEntity<EpisodeResponse> getEpisodeById(@PathVariable("id") String id) {
        Optional<EpisodeResponse> episodeResponseOptional = episodeService.getEpisodeById(id);

        if (episodeResponseOptional.isPresent()) {
            EpisodeResponse episodeResponse = episodeResponseOptional.get();

            return new ResponseEntity<>(episodeResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<EpisodeResponse> getAllEpisodes() {
        return episodeService.getAllEpisodes();
    }

    @PostMapping
    public ResponseEntity<EpisodeResponse> createEpisode(@RequestBody EpisodeRequest episodeRequest) {
       EpisodeResponse episodeResponse = episodeService.createEpisode(episodeRequest);

       if (episodeResponse == null) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
       return new ResponseEntity<>(episodeResponse, HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEpisode(@PathVariable String id) {
        episodeService.deleteEpisode(id);
    }
}

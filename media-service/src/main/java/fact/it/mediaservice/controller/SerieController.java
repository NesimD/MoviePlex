package fact.it.mediaservice.controller;

import fact.it.mediaservice.dto.serie.SerieRequest;
import fact.it.mediaservice.dto.serie.SerieResponse;
import fact.it.mediaservice.service.SerieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/media/serie")
@RequiredArgsConstructor
public class SerieController {
    private final SerieService serieService;

    @GetMapping("{mediaCode}")
    @ResponseStatus(HttpStatus.OK)
    public SerieResponse getSerieByMediaCode(@PathVariable("mediaCode") String mediaCode) {
        return serieService.getSerieByMediaCode(mediaCode);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SerieResponse> getSeriesByMediaCode(@RequestParam List<String> mediaCodes) {
        return serieService.getSeriesByMediaCodes(mediaCodes);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<SerieResponse> getAllSeries() {
        return serieService.getAllSeries();
    }

    @PostMapping
    public ResponseEntity<SerieResponse> createSerie(@RequestBody SerieRequest SerieRequest) {
        SerieResponse serieResponse = serieService.createSerie(SerieRequest);

        if (serieResponse == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(serieResponse, HttpStatus.CREATED);
    }

    @PutMapping("{mediaCode}")
    public ResponseEntity<SerieResponse> updateSerie(@RequestBody SerieRequest serieRequest, @PathVariable("mediaCode") String mediaCode) {
        SerieResponse serieResponse = serieService.updateSerieByMediaCode(serieRequest, mediaCode);

        if (serieResponse == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(serieResponse, HttpStatus.OK);
    }
}

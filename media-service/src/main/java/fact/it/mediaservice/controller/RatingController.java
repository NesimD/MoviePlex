package fact.it.mediaservice.controller;

import fact.it.mediaservice.dto.rating.RatingRequest;
import fact.it.mediaservice.dto.rating.RatingResponse;
import fact.it.mediaservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/media/rating")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @GetMapping("{id}")
    public ResponseEntity<RatingResponse> getRatingById(@PathVariable("id") String id) {
        Optional<RatingResponse> ratingResponseOptional = ratingService.getRatingById(id);

        if (ratingResponseOptional.isPresent()) {
            RatingResponse ratingResponse = ratingResponseOptional.get();

            return new ResponseEntity<>(ratingResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<RatingResponse> getAllRatings() {
        return ratingService.getAllRatings();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createRating(@RequestBody RatingRequest ratingRequest) {
        ratingService.createRating(ratingRequest);
    }

    @Transactional
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRating(@PathVariable String id) {
        ratingService.deleteRating(id);
    }
}

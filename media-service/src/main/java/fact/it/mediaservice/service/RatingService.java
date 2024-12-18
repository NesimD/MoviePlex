package fact.it.mediaservice.service;

import fact.it.mediaservice.dto.rating.RatingRequest;
import fact.it.mediaservice.dto.rating.RatingResponse;
import fact.it.mediaservice.model.Rating;
import fact.it.mediaservice.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;

    public Optional<RatingResponse> getRatingById(String id) {
        Optional<Rating> ratingOptional = ratingRepository.findById(id);

        if (ratingOptional.isPresent()) {
            Rating rating = ratingOptional.get();
            RatingResponse ratingResponse = mapToRatingResponse(rating);

            return Optional.of(ratingResponse);
        }
        return Optional.empty();
    }

    public List<RatingResponse> getAllRatings() {
        List<Rating> ratings = ratingRepository.findAll();

        return ratings.stream().map(this::mapToRatingResponse).toList();
    }

    public RatingResponse createRating(RatingRequest ratingRequest){

        Rating rating = Rating.builder()
                .name(ratingRequest.getName())
                .description(ratingRequest.getDescription())
                .build();

        ratingRepository.save(rating);
        return mapToRatingResponse(rating);
    }

    public void deleteRating(String id) {
        ratingRepository.deleteById(id);
    }

    private RatingResponse mapToRatingResponse(Rating rating) {
        return RatingResponse.builder()
                .id(rating.getId())
                .name(rating.getName())
                .description(rating.getDescription())
                .build();
    }
}

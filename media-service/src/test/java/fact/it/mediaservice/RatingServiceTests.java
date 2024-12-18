package fact.it.mediaservice;

import fact.it.mediaservice.dto.rating.RatingRequest;
import fact.it.mediaservice.dto.rating.RatingResponse;
import fact.it.mediaservice.model.Rating;
import fact.it.mediaservice.repository.RatingRepository;
import fact.it.mediaservice.service.RatingService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RatingServiceTests {
    @InjectMocks
    private RatingService ratingService;

    @Mock
    private RatingRepository ratingRepository;

    @Test
    public void testGetRatingByIdRatingFound() {
        // Arrange
        Rating rating = new Rating();
        rating.setId("1");
        rating.setName("R");
        rating.setDescription("R rated movies definitely contain some adult material. Parents are strongly urged to find out more about this film before they allow their children to accompany them. An R-rated film may include strong language, violence, nudity, drug abuse, other elements, or a combination of the above.");

        when(ratingRepository.findById("1")).thenReturn(Optional.of(rating));

        // Act
        Optional<RatingResponse> ratingResponseOptional = ratingService.getRatingById("1");

        // Assert
        assertTrue(ratingResponseOptional.isPresent());
        assertEquals(rating.getId(), ratingResponseOptional.get().getId());
        assertEquals(rating.getName(), ratingResponseOptional.get().getName());
        assertEquals(rating.getDescription(), ratingResponseOptional.get().getDescription());

        verify(ratingRepository, times(1)).findById("1");
    }

    @Test
    public void testGetRatingByIdRatingNotFound() {
        // Arrange
        Rating rating = new Rating();
        rating.setId("1");
        rating.setName("R");
        rating.setDescription("R rated movies definitely contain some adult material. Parents are strongly urged to find out more about this film before they allow their children to accompany them. An R-rated film may include strong language, violence, nudity, drug abuse, other elements, or a combination of the above.");

        when(ratingRepository.findById("1")).thenReturn(Optional.of(rating));

        // Act
        Optional<RatingResponse> ratingResponseOptional = ratingService.getRatingById("100");

        // Assert
        assertEquals(Optional.empty(), ratingResponseOptional);
    }

    @Test
    public void testGetAllRatings() {
        // Arrange
        Rating rating1 = new Rating();
        rating1.setId("1");
        rating1.setName("R");
        rating1.setDescription("R rated movies definitely contain some adult material. Parents are strongly urged to find out more about this film before they allow their children to accompany them. An R-rated film may include strong language, violence, nudity, drug abuse, other elements, or a combination of the above.");

        Rating rating2 = new Rating();
        rating2.setId("1");
        rating2.setName("PG-13");
        rating2.setDescription("Parents Strongly Cautioned Some material may be inappropriate for children under 13. Parents are urged to be cautious. Some material may be inappropriate for pre-teenagers.");

        when(ratingRepository.findAll()).thenReturn(Arrays.asList(rating1, rating2));

        // Act
        List<RatingResponse> ratingResponses = ratingService.getAllRatings();

        // Assert
        assertEquals(2, ratingResponses.size());
        assertEquals(rating1.getId(), ratingResponses.get(0).getId());
        assertEquals(rating1.getName(), ratingResponses.get(0).getName());
        assertEquals(rating1.getDescription(), ratingResponses.get(0).getDescription());

        assertEquals(rating2.getId(), ratingResponses.get(1).getId());
        assertEquals(rating2.getName(), ratingResponses.get(1).getName());
        assertEquals(rating2.getDescription(), ratingResponses.get(1).getDescription());

        verify(ratingRepository, times(1)).findAll();
    }

    @Test public void testCreateRating() {
        // Arrange
        Rating rating = new Rating();
        rating.setId("1");
        rating.setName("R");
        rating.setDescription("R rated movies definitely contain some adult material. Parents are strongly urged to find out more about this film before they allow their children to accompany them. An R-rated film may include strong language, violence, nudity, drug abuse, other elements, or a combination of the above.");

        RatingRequest ratingRequest = new RatingRequest();
        ratingRequest.setName("R");
        ratingRequest.setDescription("R rated movies definitely contain some adult material. Parents are strongly urged to find out more about this film before they allow their children to accompany them. An R-rated film may include strong language, violence, nudity, drug abuse, other elements, or a combination of the above.");

        when(ratingRepository.save(Mockito.any(Rating.class))).thenReturn(rating);

        // Act
        RatingResponse ratingResponse = ratingService.createRating(ratingRequest);

        // Assert
        Assertions.assertThat(ratingResponse).isNotNull();
        assertEquals(rating.getName(), ratingResponse.getName());
        assertEquals(rating.getDescription(), ratingResponse.getDescription());
    }

    @Test public void testDeleteRating() {
        Rating rating = new Rating();
        rating.setId("1");
        rating.setName("R");
        rating.setDescription("R rated movies definitely contain some adult material. Parents are strongly urged to find out more about this film before they allow their children to accompany them. An R-rated film may include strong language, violence, nudity, drug abuse, other elements, or a combination of the above.");

        when(ratingRepository.findById("1")).thenReturn(Optional.of(rating));

        ratingService.deleteRating("1");

        verify(ratingRepository, times(1)).deleteById("1");
    }
}

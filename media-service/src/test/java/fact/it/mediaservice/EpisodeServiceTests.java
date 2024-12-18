package fact.it.mediaservice;

import fact.it.mediaservice.dto.episode.EpisodeRequest;
import fact.it.mediaservice.dto.episode.EpisodeResponse;
import fact.it.mediaservice.model.Episode;
import fact.it.mediaservice.model.Rating;
import fact.it.mediaservice.repository.EpisodeRepository;
import fact.it.mediaservice.repository.RatingRepository;
import fact.it.mediaservice.service.EpisodeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EpisodeServiceTests {
    @InjectMocks
    private EpisodeService episodeService;

    @Mock
    private EpisodeRepository episodeRepository;

    @Mock
    private RatingRepository ratingRepository;

    @Test
    public void testGetEpisodeByIdEpisodeFound() {
        // Arrange
        Rating rating = new Rating();
        rating.setId("1");
        rating.setName("PG-13");
        rating.setDescription("Rated PG-13 for reckless and illegal behavior involving teens, violence, language and sexual content");

        Episode episode = new Episode();
        episode.setId("1");
        episode.setTitle("Long Day's Journey Into Night");
        episode.setDescription("The Matthews' family road trip takes a horrifying turn when they are detoured to a small town from which they cannot leave. When their family RV crashes, Sheriff Boyd Stevens and other residents rush to save them before the sun goes down.");
        episode.setDuration(52);
        episode.setReleaseDate(new Date("11/12/2020"));
        episode.setRating(rating);

        when(episodeRepository.findById("1")).thenReturn(Optional.of(episode));

        // Act
        Optional<EpisodeResponse> episodeResponseOptional = episodeService.getEpisodeById("1");

        // Assert
        assertTrue(episodeResponseOptional.isPresent());
        assertEquals(episode.getId(), episodeResponseOptional.get().getId());
        assertEquals(episode.getTitle(), episodeResponseOptional.get().getTitle());
        assertEquals(episode.getDescription(), episodeResponseOptional.get().getDescription());
        assertEquals(episode.getDuration(), episodeResponseOptional.get().getDuration());
        assertEquals(episode.getReleaseDate(), episodeResponseOptional.get().getReleaseDate());
        assertEquals(episode.getRating().getId(), episodeResponseOptional.get().getRating().getId());
        assertEquals(episode.getRating().getName(), episodeResponseOptional.get().getRating().getName());
        assertEquals(episode.getRating().getDescription(), episodeResponseOptional.get().getRating().getDescription());

        verify(episodeRepository, times(1)).findById("1");
    }

    @Test
    public void testGetEpisodeByIdEpisodeNotFound() {
        // Arrange
        Rating rating = new Rating();
        rating.setId("1");
        rating.setName("PG-13");
        rating.setDescription("Rated PG-13 for reckless and illegal behavior involving teens, violence, language and sexual content");

        Episode episode = new Episode();
        episode.setId("1");
        episode.setTitle("Long Day's Journey Into Night");
        episode.setDescription("The Matthews' family road trip takes a horrifying turn when they are detoured to a small town from which they cannot leave. When their family RV crashes, Sheriff Boyd Stevens and other residents rush to save them before the sun goes down.");
        episode.setDuration(52);
        episode.setReleaseDate(new Date("11/12/2020"));
        episode.setRating(rating);

        when(episodeRepository.findById("1")).thenReturn(Optional.of(episode));

        // Act
        Optional<EpisodeResponse> episodeResponseOptional = episodeService.getEpisodeById("100");

        // Assert
        assertEquals(Optional.empty(), episodeResponseOptional);
    }

    @Test
    public void testGetAllEpisodes() {
        // Arrange
        Rating rating = new Rating();
        rating.setId("1");
        rating.setName("PG-13");
        rating.setDescription("Rated PG-13 for reckless and illegal behavior involving teens, violence, language and sexual content");

        Episode episode1 = new Episode();
        episode1.setId("1");
        episode1.setTitle("Long Day's Journey Into Night");
        episode1.setDescription("The Matthews' family road trip takes a horrifying turn when they are detoured to a small town from which they cannot leave. When their family RV crashes, Sheriff Boyd Stevens and other residents rush to save them before the sun goes down.");
        episode1.setDuration(52);
        episode1.setReleaseDate(new Date("11/12/2020"));
        episode1.setRating(rating);

        Episode episode2 = new Episode();
        episode2.setId("2");
        episode2.setTitle("Welcome to the Playground");
        episode2.setDescription("Orphaned sisters Vi and Powder bring trouble to Zaun's underground streets in the wake of a heist in posh Piltover.");
        episode2.setDuration(43);
        episode2.setReleaseDate(new Date("11/12/2020"));
        episode2.setRating(rating);


        when(episodeRepository.findAll()).thenReturn(Arrays.asList(episode1, episode2));

        // Act
        List<EpisodeResponse> episodeResponses = episodeService.getAllEpisodes();

        // Assert
        assertEquals(2, episodeResponses.size());
        assertEquals(episode1.getId(), episodeResponses.get(0).getId());
        assertEquals(episode1.getTitle(), episodeResponses.get(0).getTitle());
        assertEquals(episode1.getDescription(), episodeResponses.get(0).getDescription());
        assertEquals(episode1.getDuration(), episodeResponses.get(0).getDuration());
        assertEquals(episode1.getReleaseDate(), episodeResponses.get(0).getReleaseDate());
        assertEquals(episode1.getRating().getId(), episodeResponses.get(0).getRating().getId());
        assertEquals(episode1.getRating().getName(), episodeResponses.get(0).getRating().getName());
        assertEquals(episode1.getRating().getDescription(), episodeResponses.get(0).getRating().getDescription());

        assertEquals(episode2.getId(), episodeResponses.get(1).getId());
        assertEquals(episode2.getTitle(), episodeResponses.get(1).getTitle());
        assertEquals(episode2.getDescription(), episodeResponses.get(1).getDescription());
        assertEquals(episode2.getDuration(), episodeResponses.get(1).getDuration());
        assertEquals(episode2.getReleaseDate(), episodeResponses.get(1).getReleaseDate());
        assertEquals(episode2.getRating().getId(), episodeResponses.get(1).getRating().getId());
        assertEquals(episode2.getRating().getName(), episodeResponses.get(1).getRating().getName());
        assertEquals(episode2.getRating().getDescription(), episodeResponses.get(1).getRating().getDescription());

        verify(episodeRepository, times(1)).findAll();
    }

    @Test public void testCreateEpisode() {
        // Arrange
        Rating rating = new Rating();
        rating.setId("1");
        rating.setName("PG-13");
        rating.setDescription("Rated PG-13 for reckless and illegal behavior involving teens, violence, language and sexual content");

        Episode episode = new Episode();
        episode.setId("1");
        episode.setTitle("Long Day's Journey Into Night");
        episode.setDescription("The Matthews' family road trip takes a horrifying turn when they are detoured to a small town from which they cannot leave. When their family RV crashes, Sheriff Boyd Stevens and other residents rush to save them before the sun goes down.");
        episode.setDuration(52);
        episode.setReleaseDate(new Date("11/12/2020"));
        episode.setRating(rating);

        EpisodeRequest episodeRequest = new EpisodeRequest();
        episodeRequest.setTitle("Long Day's Journey Into Night");
        episodeRequest.setDescription("The Matthews' family road trip takes a horrifying turn when they are detoured to a small town from which they cannot leave. When their family RV crashes, Sheriff Boyd Stevens and other residents rush to save them before the sun goes down.");
        episodeRequest.setDuration(52);
        episodeRequest.setReleaseDate(new Date("11/12/2020"));
        episodeRequest.setRatingId("1");

        when(episodeRepository.save(Mockito.any(Episode.class))).thenReturn(episode);

        // Act
        EpisodeResponse episodeResponse = episodeService.createEpisode(episodeRequest);

        // Assert
        Assertions.assertThat(episodeResponse).isNotNull();
    }

    @Test public void testDeleteEpisode() {
        Rating rating = new Rating();
        rating.setId("1");
        rating.setName("PG-13");
        rating.setDescription("Rated PG-13 for reckless and illegal behavior involving teens, violence, language and sexual content");

        Episode episode = new Episode();
        episode.setId("1");
        episode.setTitle("Long Day's Journey Into Night");
        episode.setDescription("The Matthews' family road trip takes a horrifying turn when they are detoured to a small town from which they cannot leave. When their family RV crashes, Sheriff Boyd Stevens and other residents rush to save them before the sun goes down.");
        episode.setDuration(52);
        episode.setReleaseDate(new Date("11/12/2020"));
        episode.setRating(rating);

        when(episodeRepository.findById("1")).thenReturn(Optional.of(episode));

        episodeService.deleteEpisode("1");

        verify(episodeRepository, times(1)).deleteById("1");
    }
}

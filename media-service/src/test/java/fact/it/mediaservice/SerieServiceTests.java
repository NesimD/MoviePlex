package fact.it.mediaservice;

import fact.it.mediaservice.dto.serie.SerieRequest;
import fact.it.mediaservice.dto.serie.SerieResponse;
import fact.it.mediaservice.model.Episode;
import fact.it.mediaservice.model.Genre;
import fact.it.mediaservice.model.Rating;
import fact.it.mediaservice.model.Serie;
import fact.it.mediaservice.repository.EpisodeRepository;
import fact.it.mediaservice.repository.GenreRepository;
import fact.it.mediaservice.repository.RatingRepository;
import fact.it.mediaservice.repository.SerieRepository;
import fact.it.mediaservice.service.SerieService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SerieServiceTests {
    @InjectMocks
    private SerieService serieService;

    @Mock
    private SerieRepository serieRepository;

    @Mock
    private EpisodeRepository episodeRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private RatingRepository ratingRepository;

    @Test
    public void testGetSerieByMediaCode() {
        // Arrange
        Serie serie = new Serie();
        Genre genre = new Genre();
        Rating rating = new Rating();
        Episode episode = new Episode();

        genre.setId("1");
        genre.setName("Action");

        rating.setId("1");
        rating.setName("PG-13");
        rating.setDescription("Rated PG-13 for reckless and illegal behavior involving teens, violence, language and sexual content");

        episode.setId("1");
        episode.setTitle("Long Day's Journey Into Night");
        episode.setDescription("The Matthews' family road trip takes a horrifying turn when they are detoured to a small town from which they cannot leave. When their family RV crashes, Sheriff Boyd Stevens and other residents rush to save them before the sun goes down.");
        episode.setDuration(52);
        episode.setReleaseDate(new Date("11/12/2020"));
        episode.setRating(rating);

        serie.setMediaCode("ss001");
        serie.setTitle("From");
        serie.setReleaseDate(new Date("11/12/2020"));
        serie.setSeasons(4);
        serie.setEpisode(episode);
        serie.setGenre(genre);
        serie.setRating(rating);
        serie.setReviewScore(8);

        when(serieRepository.findSerieByMediaCode("ss001")).thenReturn(serie);

        // Act
        SerieResponse serieResponse = serieService.getSerieByMediaCode("ss001");

        // Assert
        assertEquals(serie.getMediaCode(), serieResponse.getMediaCode());
        assertEquals(serie.getTitle(), serieResponse.getTitle());
        assertEquals(serie.getReleaseDate(), serieResponse.getReleaseDate());
        assertEquals(serie.getSeasons(), serieResponse.getSeasons());
        assertEquals(serie.getEpisode().getId(), serieResponse.getEpisode().getId());
        assertEquals(serie.getEpisode().getTitle(), serieResponse.getEpisode().getTitle());
        assertEquals(serie.getEpisode().getDescription(), serieResponse.getEpisode().getDescription());
        assertEquals(serie.getEpisode().getDuration(), serieResponse.getEpisode().getDuration());
        assertEquals(serie.getReleaseDate(), serieResponse.getEpisode().getReleaseDate());
        assertEquals(serie.getRating().getId(), serieResponse.getEpisode().getRating().getId());
        assertEquals(serie.getRating().getName(), serieResponse.getEpisode().getRating().getName());
        assertEquals(serie.getRating().getDescription(), serieResponse.getEpisode().getRating().getDescription());
        assertEquals(serie.getGenre().getId(), serieResponse.getGenre().getId());
        assertEquals(serie.getGenre().getName(), serieResponse.getGenre().getName());
        assertEquals(serie.getRating().getId(), serieResponse.getRating().getId());
        assertEquals(serie.getRating().getName(), serieResponse.getRating().getName());
        assertEquals(serie.getRating().getDescription(), serieResponse.getRating().getDescription());
        assertEquals(serie.getReviewScore(), serieResponse.getReviewScore());

        verify(serieRepository, times(1)).findSerieByMediaCode("ss001");
    }

    @Test
    public void testGetSeriesByMediaCodes() {
        // Arrange
        Serie serie1 = new Serie();
        Genre genre1 = new Genre();
        Rating rating1 = new Rating();
        Episode episode1 = new Episode();

        genre1.setId("1");
        genre1.setName("Action");

        rating1.setId("1");
        rating1.setName("PG-13");
        rating1.setDescription("Rated PG-13 for reckless and illegal behavior involving teens, violence, language and sexual content");

        episode1.setId("1");
        episode1.setTitle("Long Day's Journey Into Night");
        episode1.setDescription("The Matthews' family road trip takes a horrifying turn when they are detoured to a small town from which they cannot leave. When their family RV crashes, Sheriff Boyd Stevens and other residents rush to save them before the sun goes down.");
        episode1.setDuration(52);
        episode1.setReleaseDate(new Date("11/12/2020"));
        episode1.setRating(rating1);

        serie1.setMediaCode("ss001");
        serie1.setTitle("From");
        serie1.setGenre(genre1);
        serie1.setSeasons(4);
        serie1.setEpisode(episode1);
        serie1.setReleaseDate(new Date("11/12/2020"));
        serie1.setRating(rating1);
        serie1.setReviewScore(8);

        Serie serie2 = new Serie();
        Genre genre2 = new Genre();
        Rating rating2 = new Rating();
        Episode episode2 = new Episode();

        genre2.setId("1");
        genre2.setName("Action");

        rating2.setId("1");
        rating2.setName("PG-13");
        rating2.setDescription("Rated PG-13 for reckless and illegal behavior involving teens, violence, language and sexual content");

        episode2.setId("2");
        episode2.setTitle("Welcome to the Playground");
        episode2.setDescription("Orphaned sisters Vi and Powder bring trouble to Zaun's underground streets in the wake of a heist in posh Piltover.");
        episode2.setDuration(43);
        episode2.setReleaseDate(new Date("11/12/2020"));
        episode2.setRating(rating1);

        serie2.setMediaCode("ss002");
        serie2.setTitle("Arcane");
        serie2.setGenre(genre1);
        serie2.setSeasons(2);
        serie2.setEpisode(episode2);
        serie2.setReleaseDate(new Date("11/12/2020"));
        serie2.setRating(rating1);
        serie2.setReviewScore(8);

        when(serieRepository.findSeriesByMediaCodeIn(Arrays.asList("ss001", "ss002"))).thenReturn(Arrays.asList(serie1, serie2));

        // Act
        List<SerieResponse> serieResponses = serieService.getSeriesByMediaCodes(Arrays.asList("ss001", "ss002"));

        // Assert
        assertEquals(serie1.getMediaCode(), serieResponses.get(0).getMediaCode());
        assertEquals(serie1.getTitle(), serieResponses.get(0).getTitle());
        assertEquals(serie1.getGenre().getId(), serieResponses.get(0).getGenre().getId());
        assertEquals(serie1.getGenre().getName(), serieResponses.get(0).getGenre().getName());
        assertEquals(serie1.getReleaseDate(), serieResponses.get(0).getReleaseDate());
        assertEquals(serie1.getRating().getId(), serieResponses.get(0).getRating().getId());
        assertEquals(serie1.getRating().getName(), serieResponses.get(0).getRating().getName());
        assertEquals(serie1.getRating().getDescription(), serieResponses.get(0).getRating().getDescription());
        assertEquals(serie1.getReviewScore(), serieResponses.get(0).getReviewScore());

        assertEquals(serie2.getMediaCode(), serieResponses.get(1).getMediaCode());
        assertEquals(serie2.getTitle(), serieResponses.get(1).getTitle());
        assertEquals(serie2.getGenre().getId(), serieResponses.get(1).getGenre().getId());
        assertEquals(serie2.getGenre().getName(), serieResponses.get(1).getGenre().getName());
        assertEquals(serie2.getReleaseDate(), serieResponses.get(1).getReleaseDate());
        assertEquals(serie2.getRating().getId(), serieResponses.get(1).getRating().getId());
        assertEquals(serie2.getRating().getName(), serieResponses.get(1).getRating().getName());
        assertEquals(serie2.getRating().getDescription(), serieResponses.get(1).getRating().getDescription());
        assertEquals(serie2.getReviewScore(), serieResponses.get(1).getReviewScore());

        verify(serieRepository, times(1)).findSeriesByMediaCodeIn(Arrays.asList("ss001", "ss002"));
    }

    @Test
    public void testGetAllSeries() {
        // Arrange
        Serie serie1 = new Serie();
        Genre genre1 = new Genre();
        Rating rating1 = new Rating();
        Episode episode1 = new Episode();

        genre1.setId("1");
        genre1.setName("Action");

        rating1.setId("1");
        rating1.setName("PG-13");
        rating1.setDescription("Rated PG-13 for reckless and illegal behavior involving teens, violence, language and sexual content");

        episode1.setId("1");
        episode1.setTitle("Long Day's Journey Into Night");
        episode1.setDescription("The Matthews' family road trip takes a horrifying turn when they are detoured to a small town from which they cannot leave. When their family RV crashes, Sheriff Boyd Stevens and other residents rush to save them before the sun goes down.");
        episode1.setDuration(52);
        episode1.setReleaseDate(new Date("11/12/2020"));
        episode1.setRating(rating1);

        serie1.setMediaCode("ss001");
        serie1.setTitle("From");
        serie1.setGenre(genre1);
        serie1.setSeasons(4);
        serie1.setEpisode(episode1);
        serie1.setReleaseDate(new Date("11/12/2020"));
        serie1.setRating(rating1);
        serie1.setReviewScore(8);

        Serie serie2 = new Serie();
        Genre genre2 = new Genre();
        Rating rating2 = new Rating();
        Episode episode2 = new Episode();

        genre2.setId("1");
        genre2.setName("Action");

        rating2.setId("1");
        rating2.setName("PG-13");
        rating2.setDescription("Rated PG-13 for reckless and illegal behavior involving teens, violence, language and sexual content");

        episode2.setId("2");
        episode2.setTitle("Welcome to the Playground");
        episode2.setDescription("Orphaned sisters Vi and Powder bring trouble to Zaun's underground streets in the wake of a heist in posh Piltover.");
        episode2.setDuration(43);
        episode2.setReleaseDate(new Date("11/12/2020"));
        episode2.setRating(rating1);

        serie2.setMediaCode("ss002");
        serie2.setTitle("Arcane");
        serie2.setGenre(genre1);
        serie2.setSeasons(2);
        serie2.setEpisode(episode2);
        serie2.setReleaseDate(new Date("11/12/2020"));
        serie2.setRating(rating1);
        serie2.setReviewScore(8);

        when(serieRepository.findAll()).thenReturn(Arrays.asList(serie1, serie2));

        // Act
        List<SerieResponse> serieResponses = serieService.getAllSeries();

        // Assert
        assertEquals(2, serieResponses.size());
        assertEquals(serie1.getMediaCode(), serieResponses.get(0).getMediaCode());
        assertEquals(serie1.getTitle(), serieResponses.get(0).getTitle());
        assertEquals(serie1.getGenre().getId(), serieResponses.get(0).getGenre().getId());
        assertEquals(serie1.getGenre().getName(), serieResponses.get(0).getGenre().getName());
        assertEquals(serie1.getReleaseDate(), serieResponses.get(0).getReleaseDate());
        assertEquals(serie1.getRating().getId(), serieResponses.get(0).getRating().getId());
        assertEquals(serie1.getRating().getName(), serieResponses.get(0).getRating().getName());
        assertEquals(serie1.getRating().getDescription(), serieResponses.get(0).getRating().getDescription());
        assertEquals(serie1.getReviewScore(), serieResponses.get(0).getReviewScore());

        assertEquals(serie2.getMediaCode(), serieResponses.get(1).getMediaCode());
        assertEquals(serie2.getTitle(), serieResponses.get(1).getTitle());
        assertEquals(serie2.getGenre().getId(), serieResponses.get(1).getGenre().getId());
        assertEquals(serie2.getGenre().getName(), serieResponses.get(1).getGenre().getName());
        assertEquals(serie2.getReleaseDate(), serieResponses.get(1).getReleaseDate());
        assertEquals(serie2.getRating().getId(), serieResponses.get(1).getRating().getId());
        assertEquals(serie2.getRating().getName(), serieResponses.get(1).getRating().getName());
        assertEquals(serie2.getRating().getDescription(), serieResponses.get(1).getRating().getDescription());
        assertEquals(serie2.getReviewScore(), serieResponses.get(1).getReviewScore());

        verify(serieRepository, times(1)).findAll();
    }

    @Test public void testCreateSerie() {
        // Arrange
        Serie serie = new Serie();

        Genre genre = new Genre();
        genre.setId("1");
        genre.setName("Action");

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

        serie.setMediaCode("ss001");
        serie.setTitle("From");
        serie.setReleaseDate(new Date("11/12/2020"));
        serie.setSeasons(4);
        serie.setEpisode(episode);
        serie.setGenre(genre);
        serie.setRating(rating);
        serie.setReviewScore(8);

        SerieRequest serieRequest = new SerieRequest();
        serieRequest.setMediaCode("ss001");
        serieRequest.setTitle("From");
        serieRequest.setGenreId("1");
        serieRequest.setSeasons(4);
        serieRequest.setEpisodeId("1");
        serieRequest.setReleaseDate(new Date("11/12/2020"));
        serieRequest.setRatingId("1");
        serieRequest.setReviewScore(8);

        when(serieRepository.save(Mockito.any(Serie.class))).thenReturn(serie);

        // Act
        SerieResponse serieResponse = serieService.createSerie(serieRequest);

        // Assert
        Assertions.assertThat(serieResponse).isNotNull();
    }

    @Test public void testUpdateSerieByIdSerieFound() {
        // Arrange
        String serieId = "1";
        String genreId = "1";
        String ratingId = "1";
        String episodeId = "1";

        SerieRequest serieRequest = new SerieRequest();
        serieRequest.setMediaCode("ss001");
        serieRequest.setTitle("From");
        serieRequest.setGenreId("1");
        serieRequest.setSeasons(4);
        serieRequest.setEpisodeId("1");
        serieRequest.setReleaseDate(new Date("11/12/2020"));
        serieRequest.setRatingId("1");
        serieRequest.setReviewScore(8);

        Serie serie = new Serie();
        serie.setId(serieId);
        serie.setMediaCode("ss001");
        serie.setTitle("From");
        serie.setSeasons(4);
        serie.setReleaseDate(new Date("11/12/2020"));
        serie.setReviewScore(8);

        Genre genre = new Genre();
        genre.setId(genreId);
        genre.setName("Action");

        Rating rating = new Rating();
        rating.setId(ratingId);
        rating.setName("PG-13");
        rating.setDescription("Rated PG-13 for reckless and illegal behavior involving teens, violence, language and sexual content");

        Episode episode = new Episode();
        episode.setId(episodeId);
        episode.setTitle("Long Day's Journey Into Night");
        episode.setDescription("The Matthews' family road trip takes a horrifying turn when they are detoured to a small town from which they cannot leave. When their family RV crashes, Sheriff Boyd Stevens and other residents rush to save them before the sun goes down.");
        episode.setDuration(52);
        episode.setReleaseDate(new Date("11/12/2020"));
        episode.setRating(rating);

        serie.setGenre(genre);
        serie.setRating(rating);

        when(serieRepository.findById(serieId)).thenReturn(Optional.of(serie));
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));
        when(ratingRepository.findById(ratingId)).thenReturn(Optional.of(rating));
        when(episodeRepository.findById(episodeId)).thenReturn(Optional.of(episode));

        // Act
        SerieResponse updatedSerieResponse = serieService.updateSerieById(serieRequest, serieId);

        // Assert
        assertNotNull(updatedSerieResponse);
        assertEquals(serieRequest.getMediaCode(), updatedSerieResponse.getMediaCode());
        assertEquals(serieRequest.getTitle(), updatedSerieResponse.getTitle());
        assertEquals(genre, updatedSerieResponse.getGenre());
        assertEquals(serieRequest.getSeasons(), updatedSerieResponse.getSeasons());
        assertEquals(episode, updatedSerieResponse.getEpisode());
        assertEquals(serieRequest.getReleaseDate(), updatedSerieResponse.getReleaseDate());
        assertEquals(rating, updatedSerieResponse.getRating());
        assertEquals(serieRequest.getReviewScore(), updatedSerieResponse.getReviewScore());
    }

    @Test public void testUpdateSerieByIdSerieNotFound() {
        // Arrange
        String serieId = "1";
        String genreId = "1";
        String ratingId = "1";
        String episodeId = "1";

        SerieRequest serieRequest = new SerieRequest();
        serieRequest.setMediaCode("ss001");
        serieRequest.setTitle("From");
        serieRequest.setGenreId("1");
        serieRequest.setSeasons(4);
        serieRequest.setEpisodeId("1");
        serieRequest.setReleaseDate(new Date("11/12/2020"));
        serieRequest.setRatingId("1");
        serieRequest.setReviewScore(8);

        Serie serie = new Serie();
        serie.setId(serieId);
        serie.setMediaCode("ss001");
        serie.setTitle("From");
        serie.setSeasons(4);
        serie.setReleaseDate(new Date("11/12/2020"));
        serie.setReviewScore(8);

        Genre genre = new Genre();
        genre.setId(genreId);
        genre.setName("Action");

        Rating rating = new Rating();
        rating.setId(ratingId);
        rating.setName("PG-13");
        rating.setDescription("Rated PG-13 for reckless and illegal behavior involving teens, violence, language and sexual content");

        Episode episode = new Episode();
        episode.setId(episodeId);
        episode.setTitle("Long Day's Journey Into Night");
        episode.setDescription("The Matthews' family road trip takes a horrifying turn when they are detoured to a small town from which they cannot leave. When their family RV crashes, Sheriff Boyd Stevens and other residents rush to save them before the sun goes down.");
        episode.setDuration(52);
        episode.setReleaseDate(new Date("11/12/2020"));
        episode.setRating(rating);

        serie.setGenre(genre);
        serie.setRating(rating);

        when(serieRepository.findById(serieId)).thenReturn(Optional.of(serie));
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));
        when(ratingRepository.findById(ratingId)).thenReturn(Optional.of(rating));
        when(episodeRepository.findById(episodeId)).thenReturn(Optional.of(episode));

        // Act
        SerieResponse updatedSerieResponse = serieService.updateSerieById(serieRequest, "100");

        // Assert
        assertNull(updatedSerieResponse);
    }
}

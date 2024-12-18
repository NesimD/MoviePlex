package fact.it.mediaservice;

import fact.it.mediaservice.model.Director;
import fact.it.mediaservice.model.Genre;
import fact.it.mediaservice.model.Movie;
import fact.it.mediaservice.model.Rating;
import fact.it.mediaservice.repository.MovieRepository;
import fact.it.mediaservice.repository.SerieRepository;
import fact.it.mediaservice.service.MovieService;
import fact.it.mediaservice.service.SerieService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.when;

@SpringBootTest
class MediaServiceApplicationTests {

	@InjectMocks
	private MovieService movieService;

	@InjectMocks
	private SerieService seriesService;

	@Mock
	private MovieRepository movieRepository;

	@Mock
	private SerieRepository serieRepository;

	@Test
	public void testGetMovieByMediaCode() {
		// Arrange
		Movie movie = new Movie();
		Director director = new Director();
		Genre genre = new Genre();
		Rating rating = new Rating();

		director.setId("123");
		director.setFirstName("Tom");
		director.setLastName("Jassens");

		genre.setId("123");
		genre.setName("Horror");

		rating.setId("123");
		rating.setName("R");
		rating.setDescription("dsfsdfdsf");

		movie.setMediaCode("mm001");
		movie.setTitle("titleTest");
		movie.setDescription("This is a description");
		movie.setDirector(director);

		movie.setReleaseDate(new Date("11/12/2020"));
		movie.setGenre(genre);
		movie.setRating(rating);
		movie.setReviewScore(5);

		when(movieRepository.findMovieByMediaCode("mm001")).thenReturn(movie);
	}
}

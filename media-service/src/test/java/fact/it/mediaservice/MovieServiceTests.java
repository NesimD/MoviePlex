package fact.it.mediaservice;

import fact.it.mediaservice.dto.movie.MovieRequest;
import fact.it.mediaservice.dto.movie.MovieResponse;
import fact.it.mediaservice.model.Director;
import fact.it.mediaservice.model.Genre;
import fact.it.mediaservice.model.Movie;
import fact.it.mediaservice.model.Rating;
import fact.it.mediaservice.repository.DirectorRepository;
import fact.it.mediaservice.repository.GenreRepository;
import fact.it.mediaservice.repository.MovieRepository;
import fact.it.mediaservice.repository.RatingRepository;
import fact.it.mediaservice.service.MovieService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class MovieServiceTests {
	@InjectMocks
	private MovieService movieService;

	@Mock
	private MovieRepository movieRepository;

	@Mock
	private DirectorRepository directorRepository;

	@Mock
	private GenreRepository genreRepository;

	@Mock
	private RatingRepository ratingRepository;

	@Test
	public void testGetMovieByMediaCode() {
		// Arrange
		Movie movie = new Movie();
		Director director = new Director();
		Genre genre = new Genre();
		Rating rating = new Rating();

		director.setId("1");
		director.setFirstName("Tom");
		director.setLastName("Jassens");

		genre.setId("1");
		genre.setName("Action");

		rating.setId("1");
		rating.setName("PG-13");
		rating.setDescription("Rated PG-13 for reckless and illegal behavior involving teens, violence, language and sexual content");

		movie.setMediaCode("mm001");
		movie.setTitle("The Fast and the Furious: Tokyo Drift");
		movie.setDescription("A teenager becomes a major competitor in the world of drift racing after moving in with his father in Tokyo to avoid a jail sentence in America.");
		movie.setDirector(director);

		movie.setReleaseDate(new Date("11/12/2020"));
		movie.setGenre(genre);
		movie.setRating(rating);
		movie.setReviewScore(8);

		when(movieRepository.findMovieByMediaCode("mm001")).thenReturn(movie);

		// Act
		MovieResponse movieResponse = movieService.getMovieByMediaCode("mm001");

		// Assert
		assertEquals(movie.getMediaCode(), movieResponse.getMediaCode());
		assertEquals(movie.getTitle(), movieResponse.getTitle());
		assertEquals(movie.getDescription(), movieResponse.getDescription());
		assertEquals(movie.getDirector().getId(), movieResponse.getDirector().getId());
		assertEquals(movie.getDirector().getFirstName(), movieResponse.getDirector().getFirstName());
		assertEquals(movie.getDirector().getLastName(), movieResponse.getDirector().getLastName());
		assertEquals(movie.getReleaseDate(), movieResponse.getReleaseDate());
		assertEquals(movie.getGenre().getId(), movieResponse.getGenre().getId());
		assertEquals(movie.getGenre().getName(), movieResponse.getGenre().getName());
		assertEquals(movie.getRating().getId(), movieResponse.getRating().getId());
		assertEquals(movie.getRating().getName(), movieResponse.getRating().getName());
		assertEquals(movie.getRating().getDescription(), movieResponse.getRating().getDescription());
		assertEquals(movie.getReviewScore(), movieResponse.getReviewScore());

		verify(movieRepository, times(1)).findMovieByMediaCode("mm001");
	}

	@Test
	public void testGetMoviesByMediaCodes() {
		// Arrange
		Movie movie1 = new Movie();
		Director director1 = new Director();
		Genre genre1 = new Genre();
		Rating rating1 = new Rating();

		director1.setId("1");
		director1.setFirstName("Tom");
		director1.setLastName("Jassens");

		genre1.setId("1");
		genre1.setName("Action");

		rating1.setId("1");
		rating1.setName("PG-13");
		rating1.setDescription("Rated PG-13 for reckless and illegal behavior involving teens, violence, language and sexual content");

		movie1.setMediaCode("mm001");
		movie1.setTitle("The Fast and the Furious: Tokyo Drift");
		movie1.setDescription("A teenager becomes a major competitor in the world of drift racing after moving in with his father in Tokyo to avoid a jail sentence in America.");
		movie1.setDirector(director1);

		movie1.setReleaseDate(new Date("11/12/2020"));
		movie1.setGenre(genre1);
		movie1.setRating(rating1);
		movie1.setReviewScore(8);

		Movie movie2 = new Movie();
		Director director2 = new Director();
		Genre genre2 = new Genre();
		Rating rating2 = new Rating();

		director2.setId("2");
		director2.setFirstName("Jan");
		director2.setLastName("Jassens");

		genre2.setId("1");
		genre2.setName("Action");

		rating2.setId("1");
		rating2.setName("PG-13");
		rating2.setDescription("Rated PG-13 for reckless and illegal behavior involving teens, violence, language and sexual content");

		movie2.setMediaCode("mm002");
		movie2.setTitle("Fast & Furious");
		movie2.setDescription("Brian O'Conner, back working for the FBI in Los Angeles, teams up with Dominic Toretto to bring down a heroin importer by infiltrating his operation.");
		movie2.setDirector(director2);

		movie2.setReleaseDate(new Date("11/12/2020"));
		movie2.setGenre(genre2);
		movie2.setRating(rating2);
		movie2.setReviewScore(8);

		when(movieRepository.findMoviesByMediaCodeIn(Arrays.asList("mm001", "mm002"))).thenReturn(Arrays.asList(movie1, movie2));

		// Act
		List<MovieResponse> movieResponses = movieService.getMoviesByMediaCode(Arrays.asList("mm001", "mm002"));

		// Assert
		assertEquals(movie1.getMediaCode(), movieResponses.get(0).getMediaCode());
		assertEquals(movie1.getTitle(), movieResponses.get(0).getTitle());
		assertEquals(movie1.getDescription(), movieResponses.get(0).getDescription());
		assertEquals(movie1.getDirector().getId(), movieResponses.get(0).getDirector().getId());
		assertEquals(movie1.getDirector().getFirstName(), movieResponses.get(0).getDirector().getFirstName());
		assertEquals(movie1.getDirector().getLastName(), movieResponses.get(0).getDirector().getLastName());
		assertEquals(movie1.getReleaseDate(), movieResponses.get(0).getReleaseDate());
		assertEquals(movie1.getGenre().getId(), movieResponses.get(0).getGenre().getId());
		assertEquals(movie1.getGenre().getName(), movieResponses.get(0).getGenre().getName());
		assertEquals(movie1.getRating().getId(), movieResponses.get(0).getRating().getId());
		assertEquals(movie1.getRating().getName(), movieResponses.get(0).getRating().getName());
		assertEquals(movie1.getRating().getDescription(), movieResponses.get(0).getRating().getDescription());
		assertEquals(movie1.getReviewScore(), movieResponses.get(0).getReviewScore());

		assertEquals(movie2.getMediaCode(), movieResponses.get(1).getMediaCode());
		assertEquals(movie2.getTitle(), movieResponses.get(1).getTitle());
		assertEquals(movie2.getDescription(), movieResponses.get(1).getDescription());
		assertEquals(movie2.getDirector().getId(), movieResponses.get(1).getDirector().getId());
		assertEquals(movie2.getDirector().getFirstName(), movieResponses.get(1).getDirector().getFirstName());
		assertEquals(movie2.getDirector().getLastName(), movieResponses.get(1).getDirector().getLastName());
		assertEquals(movie2.getReleaseDate(), movieResponses.get(1).getReleaseDate());
		assertEquals(movie2.getGenre().getId(), movieResponses.get(1).getGenre().getId());
		assertEquals(movie2.getGenre().getName(), movieResponses.get(1).getGenre().getName());
		assertEquals(movie2.getRating().getId(), movieResponses.get(1).getRating().getId());
		assertEquals(movie2.getRating().getName(), movieResponses.get(1).getRating().getName());
		assertEquals(movie2.getRating().getDescription(), movieResponses.get(1).getRating().getDescription());
		assertEquals(movie2.getReviewScore(), movieResponses.get(1).getReviewScore());

		verify(movieRepository, times(1)).findMoviesByMediaCodeIn(Arrays.asList("mm001", "mm002"));
	}

	@Test
	public void testGetAllMovies() {
		// Arrange
		Movie movie1 = new Movie();
		Director director1 = new Director();
		Genre genre1 = new Genre();
		Rating rating1 = new Rating();

		director1.setId("1");
		director1.setFirstName("Tom");
		director1.setLastName("Jassens");

		genre1.setId("1");
		genre1.setName("Action");

		rating1.setId("1");
		rating1.setName("PG-13");
		rating1.setDescription("Rated PG-13 for reckless and illegal behavior involving teens, violence, language and sexual content");

		movie1.setMediaCode("mm001");
		movie1.setTitle("The Fast and the Furious: Tokyo Drift");
		movie1.setDescription("A teenager becomes a major competitor in the world of drift racing after moving in with his father in Tokyo to avoid a jail sentence in America.");
		movie1.setDirector(director1);

		movie1.setReleaseDate(new Date("11/12/2020"));
		movie1.setGenre(genre1);
		movie1.setRating(rating1);
		movie1.setReviewScore(8);

		Movie movie2 = new Movie();
		Director director2 = new Director();
		Genre genre2 = new Genre();
		Rating rating2 = new Rating();

		director2.setId("2");
		director2.setFirstName("Jan");
		director2.setLastName("Jassens");

		genre2.setId("1");
		genre2.setName("Action");

		rating2.setId("1");
		rating2.setName("PG-13");
		rating2.setDescription("Rated PG-13 for reckless and illegal behavior involving teens, violence, language and sexual content");

		movie2.setMediaCode("mm002");
		movie2.setTitle("Fast & Furious");
		movie2.setDescription("Brian O'Conner, back working for the FBI in Los Angeles, teams up with Dominic Toretto to bring down a heroin importer by infiltrating his operation.");
		movie2.setDirector(director2);

		movie2.setReleaseDate(new Date("11/12/2020"));
		movie2.setGenre(genre2);
		movie2.setRating(rating2);
		movie2.setReviewScore(8);

		when(movieRepository.findAll()).thenReturn(Arrays.asList(movie1, movie2));

		// Act
		List<MovieResponse> movieResponses = movieService.getAllMovies();

		// Assert
		assertEquals(movie1.getMediaCode(), movieResponses.get(0).getMediaCode());
		assertEquals(movie1.getTitle(), movieResponses.get(0).getTitle());
		assertEquals(movie1.getDescription(), movieResponses.get(0).getDescription());
		assertEquals(movie1.getDirector().getId(), movieResponses.get(0).getDirector().getId());
		assertEquals(movie1.getDirector().getFirstName(), movieResponses.get(0).getDirector().getFirstName());
		assertEquals(movie1.getDirector().getLastName(), movieResponses.get(0).getDirector().getLastName());
		assertEquals(movie1.getReleaseDate(), movieResponses.get(0).getReleaseDate());
		assertEquals(movie1.getGenre().getId(), movieResponses.get(0).getGenre().getId());
		assertEquals(movie1.getGenre().getName(), movieResponses.get(0).getGenre().getName());
		assertEquals(movie1.getRating().getId(), movieResponses.get(0).getRating().getId());
		assertEquals(movie1.getRating().getName(), movieResponses.get(0).getRating().getName());
		assertEquals(movie1.getRating().getDescription(), movieResponses.get(0).getRating().getDescription());
		assertEquals(movie1.getReviewScore(), movieResponses.get(0).getReviewScore());

		assertEquals(movie2.getMediaCode(), movieResponses.get(1).getMediaCode());
		assertEquals(movie2.getTitle(), movieResponses.get(1).getTitle());
		assertEquals(movie2.getDescription(), movieResponses.get(1).getDescription());
		assertEquals(movie2.getDirector().getId(), movieResponses.get(1).getDirector().getId());
		assertEquals(movie2.getDirector().getFirstName(), movieResponses.get(1).getDirector().getFirstName());
		assertEquals(movie2.getDirector().getLastName(), movieResponses.get(1).getDirector().getLastName());
		assertEquals(movie2.getReleaseDate(), movieResponses.get(1).getReleaseDate());
		assertEquals(movie2.getGenre().getId(), movieResponses.get(1).getGenre().getId());
		assertEquals(movie2.getGenre().getName(), movieResponses.get(1).getGenre().getName());
		assertEquals(movie2.getRating().getId(), movieResponses.get(1).getRating().getId());
		assertEquals(movie2.getRating().getName(), movieResponses.get(1).getRating().getName());
		assertEquals(movie2.getRating().getDescription(), movieResponses.get(1).getRating().getDescription());
		assertEquals(movie2.getReviewScore(), movieResponses.get(1).getReviewScore());

		verify(movieRepository, times(1)).findAll();
	}

	@Test public void testCreateMovie() {
		// Arrange
		MovieRequest movieRequest = new MovieRequest();
		movieRequest.setMediaCode("mm001");
		movieRequest.setTitle("The Fast and the Furious: Tokyo Drift");
		movieRequest.setDescription("A teenager becomes a major competitor in the world of drift racing after moving in with his father in Tokyo to avoid a jail sentence in America.");
		movieRequest.setDirectorId("1");
		movieRequest.setReleaseDate(new Date("11/12/2020"));
		movieRequest.setGenreId("1");
		movieRequest.setRatingId("1");
		movieRequest.setReviewScore(8);


		// Act
		movieService.createMovie(movieRequest);

		// Assert
		verify(movieRepository, times(1)).save(any(Movie.class));
	}

	@Test public void testUpdateMovieByIdMovieFound() {
		// Arrange
		String movieId = "1";
		String directorId = "1";
		String genreId = "1";
		String ratingId = "1";

		MovieRequest movieRequest = new MovieRequest();
		movieRequest.setMediaCode("mm001");
		movieRequest.setTitle("The Fast and the Furious: Tokyo Drift");
		movieRequest.setDescription("A teenager becomes a major competitor in the world of drift racing after moving in with his father in Tokyo to avoid a jail sentence in America.");
		movieRequest.setDirectorId("1");
		movieRequest.setReleaseDate(new Date("11/12/2020"));
		movieRequest.setGenreId("1");
		movieRequest.setRatingId("1");
		movieRequest.setReviewScore(8);

		Movie movie = new Movie();
		movie.setId(movieId);
		movie.setMediaCode("mm001");
		movie.setTitle("Fast & Furious");
		movie.setDescription("Brian O'Conner, back working for the FBI in Los Angeles, teams up with Dominic Toretto to bring down a heroin importer by infiltrating his operation.");
		movie.setReleaseDate(new Date("11/12/2020"));
		movie.setReviewScore(8);

		Director director = new Director();
		director.setId("1");
		director.setFirstName("Tom");
		director.setLastName("Jassens");

		Genre genre = new Genre();
		genre.setId("1");
		genre.setName("Action");

		Rating rating = new Rating();
		rating.setId("1");
		rating.setName("PG-13");
		rating.setDescription("Rated PG-13 for reckless and illegal behavior involving teens, violence, language and sexual content");

		movie.setDirector(director);
		movie.setGenre(genre);
		movie.setRating(rating);

		when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
		when(directorRepository.findById(directorId)).thenReturn(Optional.of(director));
		when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));
		when(ratingRepository.findById(ratingId)).thenReturn(Optional.of(rating));

		// Act
		MovieResponse updatedMovieResponse = movieService.updateMovieById(movieRequest, movieId);

		// Assert
		assertNotNull(updatedMovieResponse);
		assertEquals(movieRequest.getMediaCode(), updatedMovieResponse.getMediaCode());
		assertEquals(movieRequest.getTitle(), updatedMovieResponse.getTitle());
		assertEquals(movieRequest.getDescription(), updatedMovieResponse.getDescription());
		assertEquals(director, updatedMovieResponse.getDirector());
		assertEquals(movieRequest.getReleaseDate(), updatedMovieResponse.getReleaseDate());
		assertEquals(genre, updatedMovieResponse.getGenre());
		assertEquals(rating, updatedMovieResponse.getRating());
		assertEquals(movieRequest.getReviewScore(), updatedMovieResponse.getReviewScore());
	}

	@Test public void testUpdateMovieByIdMovieNotFound() {
		// Arrange
		String movieId = "1";
		String directorId = "1";
		String genreId = "1";
		String ratingId = "1";

		MovieRequest movieRequest = new MovieRequest();
		movieRequest.setMediaCode("mm001");
		movieRequest.setTitle("The Fast and the Furious: Tokyo Drift");
		movieRequest.setDescription("A teenager becomes a major competitor in the world of drift racing after moving in with his father in Tokyo to avoid a jail sentence in America.");
		movieRequest.setDirectorId("1");
		movieRequest.setReleaseDate(new Date("11/12/2020"));
		movieRequest.setGenreId("1");
		movieRequest.setRatingId("1");
		movieRequest.setReviewScore(8);

		Movie movie = new Movie();
		movie.setId(movieId);
		movie.setMediaCode("mm001");
		movie.setTitle("Fast & Furious");
		movie.setDescription("Brian O'Conner, back working for the FBI in Los Angeles, teams up with Dominic Toretto to bring down a heroin importer by infiltrating his operation.");
		movie.setReleaseDate(new Date("11/12/2020"));
		movie.setReviewScore(8);

		Director director = new Director();
		director.setId("1");
		director.setFirstName("Tom");
		director.setLastName("Jassens");

		Genre genre = new Genre();
		genre.setId("1");
		genre.setName("Action");

		Rating rating = new Rating();
		rating.setId("1");
		rating.setName("PG-13");
		rating.setDescription("Rated PG-13 for reckless and illegal behavior involving teens, violence, language and sexual content");

		movie.setDirector(director);
		movie.setGenre(genre);
		movie.setRating(rating);

		when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
		when(directorRepository.findById(directorId)).thenReturn(Optional.of(director));
		when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));
		when(ratingRepository.findById(ratingId)).thenReturn(Optional.of(rating));

		// Act
		MovieResponse updatedMovieResponse = movieService.updateMovieById(movieRequest, "100");

		// Assert
		assertNull(updatedMovieResponse);
	}
}

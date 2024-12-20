package fact.it.userservice;

import fact.it.userservice.dto.*;
import fact.it.userservice.model.*;
import fact.it.userservice.repository.FavoriteMovieRepository;
import fact.it.userservice.repository.FavoriteSerieRepository;
import fact.it.userservice.repository.UserRepository;
import fact.it.userservice.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceApplicationTests {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FavoriteMovieRepository favoriteMovieRepository;

    @Mock
    private FavoriteSerieRepository favoriteSerieRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(userService, "mediaServiceBaseUrl", "localhost:8080");
        ReflectionTestUtils.setField(userService, "subscriptionServiceBaseUrl", "localhost:8082");
    }

    @Test
    public void testGetUserById() {
        // Arrange
        List<FavoriteMovie> favoriteMovies = new ArrayList<>();
        FavoriteMovie favoriteMovie = new FavoriteMovie();
        favoriteMovie.setUser(null);
        favoriteMovie.setMediaCode("mm001");

        favoriteMovies.add(favoriteMovie);

        List<FavoriteSerie> favoriteSeries = new ArrayList<>();
        FavoriteSerie favoriteSerie = new FavoriteSerie();
        favoriteSerie.setUser(null);
        favoriteMovie.setMediaCode("ss001");

        favoriteSeries.add(favoriteSerie);

        MovieResponse movieResponse = new MovieResponse();
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

        movieResponse.setId("1");
        movieResponse.setMediaCode("mm001");
        movieResponse.setTitle("The Fast and the Furious: Tokyo Drift");
        movieResponse.setDescription("A teenager becomes a major competitor in the world of drift racing after moving in with his father in Tokyo to avoid a jail sentence in America.");
        movieResponse.setDirector(director);

        movieResponse.setReleaseDate(new Date("11/12/2020"));
        movieResponse.setGenre(genre);
        movieResponse.setRating(rating);
        movieResponse.setReviewScore(8);

        SerieResponse serieResponse = new SerieResponse();
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

        serieResponse.setMediaCode("ss001");
        serieResponse.setTitle("From");
        serieResponse.setReleaseDate(new Date("11/12/2020"));
        serieResponse.setSeasons(4);
        serieResponse.setEpisode(episode);
        serieResponse.setGenre(genre);
        serieResponse.setRating(rating);
        serieResponse.setReviewScore(8);

        User user = new User();
        user.setId(1L);
        user.setFirstName("Peter");
        user.setLastName("Janssens");
        user.setUsername("Pjan");
        user.setPassword("test");
        user.setEmail("peterjanssens@example.com");
        user.setActive(true);
        user.setSubscriptionId(1L);
        user.setFavoriteMovies(favoriteMovies);
        user.setFavoriteSeries(favoriteSeries);

        SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
        subscriptionResponse.setId(1L);
        subscriptionResponse.setName("Basic");


        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), anyLong())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(SubscriptionResponse.class)).thenReturn(Mono.just(subscriptionResponse));

        when(requestHeadersUriSpec.uri(anyString(), any(Function.class))).thenReturn(requestHeadersSpec);
        when(responseSpec.bodyToMono(MovieResponse[].class)).thenReturn(Mono.just(new MovieResponse[]{movieResponse}));

        when(responseSpec.bodyToMono(SerieResponse[].class)).thenReturn(Mono.just(new SerieResponse[]{serieResponse}));


        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        Optional<UserResponse> userResponseOptional = userService.getUserById(1L);

        // Assert
        assertTrue(userResponseOptional.isPresent());
        UserResponse userResponse = userResponseOptional.get();
        assertEquals(user.getId(), userResponse.getId());
        assertEquals(user.getFirstName(), userResponse.getFirstName());
        assertEquals(user.getLastName(), userResponse.getLastName());
        assertEquals(user.getUsername(), userResponse.getUsername());
        assertEquals(user.getEmail(), userResponse.getEmail());
        assertTrue(userResponse.isActive());
        assertEquals(subscriptionResponse, userResponse.getSubscription());
        assertEquals(movieResponse.getMediaCode(), userResponse.getFavoriteMovies()[0].getMediaCode());
        assertEquals(serieResponse.getMediaCode(), userResponse.getFavoriteSeries()[0].getMediaCode());

        verify(userRepository, times(1)).findById(1L);
    }


    @Test
    public void testGetUserByIdUserNotFound() {
        // Arrange
        List<FavoriteMovie> favoriteMovies = new ArrayList<>();
        FavoriteMovie favoriteMovie = new FavoriteMovie();
        favoriteMovie.setUser(null);
        favoriteMovie.setMediaCode("mm001");

        favoriteMovies.add(favoriteMovie);

        List<FavoriteSerie> favoriteSeries = new ArrayList<>();
        FavoriteSerie favoriteSerie = new FavoriteSerie();
        favoriteSerie.setUser(null);
        favoriteMovie.setMediaCode("ss001");

        favoriteSeries.add(favoriteSerie);

        MovieResponse movieResponse = new MovieResponse();
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

        movieResponse.setId("1");
        movieResponse.setMediaCode("mm001");
        movieResponse.setTitle("The Fast and the Furious: Tokyo Drift");
        movieResponse.setDescription("A teenager becomes a major competitor in the world of drift racing after moving in with his father in Tokyo to avoid a jail sentence in America.");
        movieResponse.setDirector(director);

        movieResponse.setReleaseDate(new Date("11/12/2020"));
        movieResponse.setGenre(genre);
        movieResponse.setRating(rating);
        movieResponse.setReviewScore(8);

        SerieResponse serieResponse = new SerieResponse();
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

        serieResponse.setMediaCode("ss001");
        serieResponse.setTitle("From");
        serieResponse.setReleaseDate(new Date("11/12/2020"));
        serieResponse.setSeasons(4);
        serieResponse.setEpisode(episode);
        serieResponse.setGenre(genre);
        serieResponse.setRating(rating);
        serieResponse.setReviewScore(8);

        User user = new User();
        user.setId(1L);
        user.setFirstName("Peter");
        user.setLastName("Janssens");
        user.setUsername("Pjan");
        user.setPassword("test");
        user.setEmail("peterjanssens@example.com");
        user.setActive(true);
        user.setSubscriptionId(1L);
        user.setFavoriteMovies(favoriteMovies);
        user.setFavoriteSeries(favoriteSeries);

        SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
        subscriptionResponse.setId(1L);
        subscriptionResponse.setName("Basic");

        // Act
        Optional<UserResponse> userResponseOptional = userService.getUserById(100L);

        // Assert
        assertFalse(userResponseOptional.isPresent());
        assertEquals(Optional.empty(), userResponseOptional);
    }

    @Test
    public void testGetAllUsers() {
        // Arrange
        List<FavoriteMovie> favoriteMovies = new ArrayList<>();
        FavoriteMovie favoriteMovie = new FavoriteMovie();
        favoriteMovie.setUser(null);
        favoriteMovie.setMediaCode("mm001");
        favoriteMovies.add(favoriteMovie);

        List<FavoriteSerie> favoriteSeries = new ArrayList<>();
        FavoriteSerie favoriteSerie = new FavoriteSerie();
        favoriteSerie.setUser(null);
        favoriteSerie.setMediaCode("ss001");
        favoriteSeries.add(favoriteSerie);

        MovieResponse movieResponse = new MovieResponse();
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

        movieResponse.setId("1");
        movieResponse.setMediaCode("mm001");
        movieResponse.setTitle("The Fast and the Furious: Tokyo Drift");
        movieResponse.setDescription("A teenager becomes a major competitor in the world of drift racing after moving in with his father in Tokyo to avoid a jail sentence in America.");
        movieResponse.setDirector(director);
        movieResponse.setReleaseDate(new Date("11/12/2020"));
        movieResponse.setGenre(genre);
        movieResponse.setRating(rating);
        movieResponse.setReviewScore(8);

        SerieResponse serieResponse = new SerieResponse();
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

        serieResponse.setMediaCode("ss001");
        serieResponse.setTitle("From");
        serieResponse.setReleaseDate(new Date("11/12/2020"));
        serieResponse.setSeasons(4);
        serieResponse.setEpisode(episode);
        serieResponse.setGenre(genre);
        serieResponse.setRating(rating);
        serieResponse.setReviewScore(8);

        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("Peter");
        user1.setLastName("Janssens");
        user1.setUsername("Pjan");
        user1.setPassword("test");
        user1.setEmail("peterjanssens@example.com");
        user1.setActive(true);
        user1.setSubscriptionId(1L);
        user1.setFavoriteMovies(favoriteMovies);
        user1.setFavoriteSeries(favoriteSeries);

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Anna");
        user2.setLastName("Smith");
        user2.setUsername("Asmith");
        user2.setPassword("test2");
        user2.setEmail("annasmith@example.com");
        user2.setActive(true);
        user2.setSubscriptionId(1L);
        user2.setFavoriteMovies(favoriteMovies);
        user2.setFavoriteSeries(favoriteSeries);

        List<User> users = Arrays.asList(user1, user2);

        SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
        subscriptionResponse.setId(1L);
        subscriptionResponse.setName("Basic");

        when(userRepository.findAll()).thenReturn(users);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(SubscriptionResponse[].class)).thenReturn(Mono.just(new SubscriptionResponse[]{subscriptionResponse}));
        when(requestHeadersUriSpec.uri(anyString(), any(Function.class))).thenReturn(requestHeadersSpec);
        when(responseSpec.bodyToMono(MovieResponse[].class)).thenReturn(Mono.just(new MovieResponse[]{movieResponse}));
        when(responseSpec.bodyToMono(SerieResponse[].class)).thenReturn(Mono.just(new SerieResponse[]{serieResponse}));

        // Act
        List<UserResponse> userResponses = userService.getAllUsers();

        // Assert
        assertEquals(2, userResponses.size());

        UserResponse userResponse1 = userResponses.get(0);
        assertEquals(user1.getId(), userResponse1.getId());
        assertEquals(user1.getFirstName(), userResponse1.getFirstName());
        assertEquals(user1.getLastName(), userResponse1.getLastName());
        assertEquals(user1.getUsername(), userResponse1.getUsername());
        assertEquals(user1.getEmail(), userResponse1.getEmail());
        assertTrue(userResponse1.isActive());
        assertEquals(subscriptionResponse, userResponse1.getSubscription());
        assertEquals(movieResponse.getMediaCode(), userResponse1.getFavoriteMovies()[0].getMediaCode());
        assertEquals(serieResponse.getMediaCode(), userResponse1.getFavoriteSeries()[0].getMediaCode());

        UserResponse userResponse2 = userResponses.get(1);
        assertEquals(user2.getId(), userResponse2.getId());
        assertEquals(user2.getFirstName(), userResponse2.getFirstName());
        assertEquals(user2.getLastName(), userResponse2.getLastName());
        assertEquals(user2.getUsername(), userResponse2.getUsername());
        assertEquals(user2.getEmail(), userResponse2.getEmail());
        assertTrue(userResponse2.isActive());
        assertEquals(subscriptionResponse, userResponse2.getSubscription());
        assertEquals(movieResponse.getMediaCode(), userResponse2.getFavoriteMovies()[0].getMediaCode());
        assertEquals(serieResponse.getMediaCode(), userResponse2.getFavoriteSeries()[0].getMediaCode());

        verify(userRepository, times(1)).findAll();
    }

    @Test public void testCreateUser() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setFirstName("Peter");
        user.setLastName("Janssens");
        user.setUsername("Pjan");
        user.setPassword("test");
        user.setEmail("peterjanssens@example.com");
        user.setActive(true);
        user.setSubscriptionId(1L);

        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("Peter");
        userRequest.setLastName("Janssens");
        userRequest.setUsername("Pjan");
        userRequest.setPassword("test");
        userRequest.setEmail("peterjanssens@example.com");
        userRequest.setActive(true);
        userRequest.setSubscriptionId(1L);

        SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
        subscriptionResponse.setId(1L);
        subscriptionResponse.setName("Basic");

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), anyLong())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(SubscriptionResponse.class)).thenReturn(Mono.just(subscriptionResponse));

        // Act
        UserResponse userResponse = userService.createUser(userRequest);

        // Assert
        assertEquals(user.getFirstName(), userResponse.getFirstName());
        assertEquals(user.getLastName(), userResponse.getLastName());
        assertEquals(user.getUsername(), userResponse.getUsername());
        assertEquals(user.getEmail(), userResponse.getEmail());
        assertTrue(userResponse.isActive());
        assertEquals(subscriptionResponse, userResponse.getSubscription());
    }

    @Test public void testAddFavoriteMovieUserFound() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setFirstName("Peter");
        user.setLastName("Janssens");
        user.setUsername("Pjan");
        user.setPassword("test");
        user.setEmail("peterjanssens@example.com");
        user.setActive(true);
        user.setSubscriptionId(1L);

        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setMediaCode("mm001");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        UserResponse userResponse = userService.addFavoriteMovie(1L, movieRequest);

        // Assert
        ArgumentCaptor<FavoriteMovie> favoriteMovieCaptor = ArgumentCaptor.forClass(FavoriteMovie.class);
        verify(favoriteMovieRepository).save(favoriteMovieCaptor.capture());
        FavoriteMovie savedFavoriteMovie = favoriteMovieCaptor.getValue();
        Assertions.assertThat(savedFavoriteMovie).isNotNull();
        Assertions.assertThat(savedFavoriteMovie.getUser()).isEqualTo(user);
        Assertions.assertThat(savedFavoriteMovie.getMediaCode()).isEqualTo("mm001");
    }

    @Test public void testAddFavoriteMovieUserNotFound() {
        // Arrange
        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setMediaCode("mm001");

        when(userRepository.findById(100L)).thenReturn(Optional.empty());

        // Act
        UserResponse userResponse = userService.addFavoriteMovie(100L, movieRequest);

        // Assert
        assertNull(userResponse);
        verifyNoInteractions(favoriteMovieRepository);
    }

    @Test public void testAddFavoriteSerieUserFound() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setFirstName("Peter");
        user.setLastName("Janssens");
        user.setUsername("Pjan");
        user.setPassword("test");
        user.setEmail("peterjanssens@example.com");
        user.setActive(true);
        user.setSubscriptionId(1L);

        SerieRequest serieRequest = new SerieRequest();
        serieRequest.setMediaCode("ss001");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        UserResponse userResponse = userService.addFavoriteSerie(1L, serieRequest);

        // Assert
        ArgumentCaptor<FavoriteSerie> favoriteSerieCaptor = ArgumentCaptor.forClass(FavoriteSerie.class);
        verify(favoriteSerieRepository).save(favoriteSerieCaptor.capture());
        FavoriteSerie savedFavoriteSerie = favoriteSerieCaptor.getValue();
        Assertions.assertThat(savedFavoriteSerie).isNotNull();
        Assertions.assertThat(savedFavoriteSerie.getUser()).isEqualTo(user);
        Assertions.assertThat(savedFavoriteSerie.getMediaCode()).isEqualTo("ss001");
    }

    @Test public void testAddFavoriteSerieUserNotFound() {
        // Arrange
        SerieRequest serieRequest = new SerieRequest();
        serieRequest.setMediaCode("ss001");

        when(userRepository.findById(100L)).thenReturn(Optional.empty());

        // Act
        UserResponse userResponse = userService.addFavoriteSerie(100L, serieRequest);

        // Assert
        assertNull(userResponse);
        verifyNoInteractions(favoriteSerieRepository);
    }

    @Test public void testDeleteFavoriteMovie() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Peter");
        user.setLastName("Janssens");
        user.setUsername("Pjan");
        user.setPassword("test");
        user.setEmail("peterjanssens@example.com");
        user.setActive(true);
        user.setSubscriptionId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.removeFavoriteMovie(1L, "mm001");

        verify(favoriteMovieRepository, times(1)).deleteByUserAndMediaCode(user, "mm001");
    }

    @Test public void testDeleteFavoriteSerie() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Peter");
        user.setLastName("Janssens");
        user.setUsername("Pjan");
        user.setPassword("test");
        user.setEmail("peterjanssens@example.com");
        user.setActive(true);
        user.setSubscriptionId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.removeFavoriteSerie(1L, "ss001");

        verify(favoriteSerieRepository, times(1)).deleteByUserAndMediaCode(user, "ss001");
    }
}

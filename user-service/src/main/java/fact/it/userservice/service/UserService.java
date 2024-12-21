package fact.it.userservice.service;

import fact.it.userservice.dto.*;
import fact.it.userservice.model.FavoriteMovie;
import fact.it.userservice.model.FavoriteSerie;
import fact.it.userservice.model.User;
import fact.it.userservice.repository.FavoriteMovieRepository;
import fact.it.userservice.repository.FavoriteSerieRepository;
import fact.it.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FavoriteMovieRepository favoriteMovieRepository;
    private final FavoriteSerieRepository favoriteSerieRepository;
    private final WebClient webClient;

    @Value("${mediaservice.baseurl}")
    private String mediaServiceBaseUrl;

    @Value("${subscriptionservice.baseurl}")
    private String subscriptionServiceBaseUrl;

    public Optional<UserResponse> getUserById(long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            UserResponse userResponse = mapToUserResponse(user);
            userResponse.setSubscription(getSubscriptionResponse(user));
            userResponse.setFavoriteMovies(getMovieResponses(user));
            userResponse.setFavoriteSeries(getSerieResponses(user));
            return Optional.of(userResponse);
        }
        return Optional.empty();
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        SubscriptionResponse[] subscriptionResponse = webClient.get()
                .uri("http://" + subscriptionServiceBaseUrl + "/api/subscription")
                .retrieve()
                .bodyToMono(SubscriptionResponse[].class)
                .block();

        Map<Long, SubscriptionResponse> subscriptionMap = Arrays.stream(subscriptionResponse)
                .collect(Collectors.toMap(SubscriptionResponse::getId, subscription -> subscription));

        return users.stream()
                .map(user -> {
                    SubscriptionResponse subscription = subscriptionMap.get(user.getSubscriptionId());

                    return UserResponse.builder()
                            .id(user.getId())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .username(user.getUsername())
                            .email(user.getEmail())
                            .active(user.isActive())
                            .subscription(subscription)
                            .favoriteMovies(getMovieResponses(user))
                            .favoriteSeries(getSerieResponses(user))
                            .build();
                })
                .collect(Collectors.toList());
    }

    public UserResponse createUser(UserRequest userRequest) {
        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .email(userRequest.getEmail())
                .active(userRequest.isActive())
                .subscriptionId(userRequest.getSubscriptionId())
                .build();

        userRepository.save(user);

        UserResponse userResponse = mapToUserResponse(user);
        userResponse.setSubscription(getSubscriptionResponse(user));

        return userResponse;
    }

    public UserResponse addFavoriteMovie(Long id, MovieRequest movieRequest) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            FavoriteMovie favoriteMovie = FavoriteMovie.builder()
                    .user(user)
                    .mediaCode(movieRequest.getMediaCode())
                    .build();

            favoriteMovieRepository.save(favoriteMovie);
            UserResponse userResponse = mapToUserResponse(user);
            userResponse.setSubscription(getSubscriptionResponse(user));
            userResponse.setFavoriteMovies(getMovieResponses(user));
            userResponse.setFavoriteSeries(getSerieResponses(user));
            return userResponse;
        }
        return null;
    }

    public UserResponse addFavoriteSerie(Long id, SerieRequest serieRequest) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            FavoriteSerie favoriteSerie = FavoriteSerie.builder()
                    .user(user)
                    .mediaCode(serieRequest.getMediaCode())
                    .build();

            favoriteSerieRepository.save(favoriteSerie);
            UserResponse userResponse = mapToUserResponse(user);
            userResponse.setSubscription(getSubscriptionResponse(user));
            userResponse.setFavoriteMovies(getMovieResponses(user));
            userResponse.setFavoriteSeries(getSerieResponses(user));
            return userResponse;
        }
        return null;
    }

    public void removeFavoriteMovie(Long id, String mediaCode) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            favoriteMovieRepository.deleteByUserAndMediaCode(userOptional.get(), mediaCode);
        }
    }

    public void removeFavoriteSerie(Long id, String mediaCode) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            favoriteSerieRepository.deleteByUserAndMediaCode(userOptional.get(), mediaCode);
        }
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .active(user.isActive())
                .build();
    }

    private MovieResponse[] getMovieResponses(User user) {
        List<String> favoriteMoviesMediaCodes = user.getFavoriteMovies()
                .stream()
                .map(FavoriteMovie::getMediaCode)
                .toList();

        return webClient.get()
                .uri("http://" + mediaServiceBaseUrl + "/api/media/movie",
                        uriBuilder -> uriBuilder.queryParam("mediaCodes", favoriteMoviesMediaCodes).build())
                .retrieve()
                .bodyToMono(MovieResponse[].class)
                .block();
    }

    private SerieResponse[] getSerieResponses(User user) {
        List<String> favoriteSeriesMediaCodes = user.getFavoriteSeries()
                .stream()
                .map(FavoriteSerie::getMediaCode)
                .toList();

        return webClient.get()
                .uri("http://" + mediaServiceBaseUrl + "/api/media/serie",
                        uriBuilder -> uriBuilder.queryParam("mediaCodes", favoriteSeriesMediaCodes).build())
                .retrieve()
                .bodyToMono(SerieResponse[].class)
                .block();
    }

    private SubscriptionResponse getSubscriptionResponse(User user) {
        return webClient.get()
                .uri("http://" + subscriptionServiceBaseUrl + "/api/subscription/{id}", user.getSubscriptionId())
                .retrieve()
                .bodyToMono(SubscriptionResponse.class)
                .block();

    }
}


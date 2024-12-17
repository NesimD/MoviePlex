package fact.it.mediaservice.service;

import fact.it.mediaservice.dto.MovieRequest;
import fact.it.mediaservice.dto.MovieResponse;
import fact.it.mediaservice.model.Movie;
import fact.it.mediaservice.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public void createMovie(MovieRequest movieRequest){
        Movie movie = Movie.builder()
                .mediaCode(movieRequest.getMediaCode())
                .title(movieRequest.getTitle())
                .description(movieRequest.getDescription())
                .director(movieRequest.getDirector())
                .releaseDate(movieRequest.getReleaseDate())
                .genre(movieRequest.getGenre())
                .rating(movieRequest.getRating())
                .reviewScore(movieRequest.getReviewScore())
                .build();

        movieRepository.save(movie);
    }

    public List<MovieResponse> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();

        return movies.stream().map(this::mapToMovieResponse).toList();
    }

    public MovieResponse getMovieByMediaCode(String mediaCode) {
        Movie movie = movieRepository.findMovieByMediaCode(mediaCode);
        return mapToMovieResponse(movie);
    }

    public List<MovieResponse> getMoviesByMediaCode(List<String> mediaCodes) {
        return movieRepository.findMoviesByMediaCodeIn(mediaCodes).stream()
                .map(this::mapToMovieResponse)
                .toList();
    }

    public MovieResponse updateMovieById(MovieRequest movieRequest, String id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);

        if (movieOptional.isPresent()) {
            Movie movie = movieOptional.get();

            movie.setMediaCode(movieRequest.getMediaCode());
            movie.setTitle(movieRequest.getTitle());
            movie.setDescription(movieRequest.getDescription());
            movie.setDirector(movieRequest.getDirector());
            movie.setReleaseDate(movieRequest.getReleaseDate());
            movie.setGenre(movieRequest.getGenre());
            movie.setRating(movieRequest.getRating());
            movie.setReviewScore(movieRequest.getReviewScore());
            return mapToMovieResponse(movie);
        }
        return null;
    }

    private MovieResponse mapToMovieResponse(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .mediaCode(movie.getMediaCode())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .director(movie.getDirector())
                .releaseDate(movie.getReleaseDate())
                .genre(movie.getGenre())
                .rating(movie.getRating())
                .reviewScore(movie.getReviewScore())
                .build();
    }
}

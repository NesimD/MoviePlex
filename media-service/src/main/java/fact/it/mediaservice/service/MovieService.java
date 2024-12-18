package fact.it.mediaservice.service;

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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;
    private final GenreRepository genreRepository;
    private final RatingRepository ratingRepository;

    public void createMovie(MovieRequest movieRequest){
        Director director = directorRepository.findById(movieRequest.getDirectorId()).orElse(null);
        Genre genre = genreRepository.findById(movieRequest.getGenreId()).orElse(null);
        Rating rating = ratingRepository.findById(movieRequest.getRatingId()).orElse(null);

        Movie movie = Movie.builder()
                .mediaCode(movieRequest.getMediaCode())
                .title(movieRequest.getTitle())
                .description(movieRequest.getDescription())
                .director(director)
                .releaseDate(movieRequest.getReleaseDate())
                .genre(genre)
                .rating(rating)
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
        Director director = directorRepository.findById(movieRequest.getDirectorId()).orElse(null);
        Genre genre = genreRepository.findById(movieRequest.getGenreId()).orElse(null);
        Rating rating = ratingRepository.findById(movieRequest.getRatingId()).orElse(null);

        if (movieOptional.isPresent()) {
            Movie movie = movieOptional.get();

            movie.setMediaCode(movieRequest.getMediaCode());
            movie.setTitle(movieRequest.getTitle());
            movie.setDescription(movieRequest.getDescription());
            movie.setDirector(director);
            movie.setReleaseDate(movieRequest.getReleaseDate());
            movie.setGenre(genre);
            movie.setRating(rating);
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

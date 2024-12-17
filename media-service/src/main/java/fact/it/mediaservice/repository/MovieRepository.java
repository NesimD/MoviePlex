package fact.it.mediaservice.repository;

import fact.it.mediaservice.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {
    Movie findMovieByMediaCode(String mediaCode);
    List<Movie> findMoviesByMediaCodeIn(List<String> mediaCodes);
}

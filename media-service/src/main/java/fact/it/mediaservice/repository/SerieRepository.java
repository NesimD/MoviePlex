package fact.it.mediaservice.repository;

import fact.it.mediaservice.model.Movie;
import fact.it.mediaservice.model.Serie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SerieRepository extends MongoRepository<Serie, String> {
    Serie findSerieByMediaCode(String mediaCode);
    List<Serie> findSeriesByMediaCodeIn(List<String> mediaCodes);
}

package fact.it.mediaservice.repository;

import fact.it.mediaservice.model.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GenreRepository extends MongoRepository<Genre, String> {
}

package fact.it.mediaservice.repository;

import fact.it.mediaservice.model.Director;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DirectorRepository extends MongoRepository<Director, String> {
}

package fact.it.mediaservice.repository;

import fact.it.mediaservice.model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RatingRepository extends MongoRepository<Rating, String> {
}

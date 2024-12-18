package fact.it.mediaservice.repository;

import fact.it.mediaservice.model.Episode;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EpisodeRepository extends MongoRepository<Episode, String> {
}

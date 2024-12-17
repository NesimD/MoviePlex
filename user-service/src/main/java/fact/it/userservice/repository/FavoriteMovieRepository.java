package fact.it.userservice.repository;

import fact.it.userservice.model.FavoriteMovie;
import fact.it.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteMovieRepository extends JpaRepository<FavoriteMovie, Long> {
    void deleteByUserAndMediaCode(User user, String mediaCode);
}

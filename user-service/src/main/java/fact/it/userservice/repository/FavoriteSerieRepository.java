package fact.it.userservice.repository;

import fact.it.userservice.model.FavoriteSerie;
import fact.it.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteSerieRepository extends JpaRepository<FavoriteSerie, Long> {
    void deleteByUserAndMediaCode(User user, String mediaCode);
}

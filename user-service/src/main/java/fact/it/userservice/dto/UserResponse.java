package fact.it.userservice.dto;

import fact.it.userservice.model.FavoriteMovie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private boolean active;
    private SubscriptionResponse subscription;
    private MovieResponse[] favoriteMovies;
    private SerieResponse[] favoriteSeries;
}

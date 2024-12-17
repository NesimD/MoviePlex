package fact.it.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private boolean active;
    private Long subscriptionId;
    private List<MovieRequest> favoriteMovies;
    private List<SerieRequest> favoriteSeries;
}

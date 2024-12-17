package fact.it.userservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private boolean active;
    private Long subscriptionId;
    @OneToMany(mappedBy = "user")
    private List<FavoriteMovie> favoriteMovies;
    @OneToMany(mappedBy = "user")
    private List<FavoriteSerie> favoriteSeries;
}

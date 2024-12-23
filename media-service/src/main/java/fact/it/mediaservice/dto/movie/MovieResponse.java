package fact.it.mediaservice.dto.movie;

import fact.it.mediaservice.model.Director;
import fact.it.mediaservice.model.Genre;
import fact.it.mediaservice.model.Rating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponse {
    private String id;
    private String mediaCode;
    private String title;
    private String description;
    private Director director;
    private Date releaseDate;
    private Genre genre;
    private Rating rating;
    private int reviewScore;
}

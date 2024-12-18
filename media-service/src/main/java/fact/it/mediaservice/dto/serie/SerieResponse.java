package fact.it.mediaservice.dto.serie;

import fact.it.mediaservice.model.Episode;
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
public class SerieResponse {
    private String id;
    private String mediaCode;
    private String title;
    private Genre genre;
    private int seasons;
    private Episode episode;
    private Date releaseDate;
    private Rating rating;
    private int reviewScore;
}

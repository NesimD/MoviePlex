package fact.it.mediaservice.dto.serie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SerieRequest {
    private String mediaCode;
    private String title;
    private String genreId;
    private int seasons;
    private String episodeId;
    private Date releaseDate;
    private String ratingId;
    private int reviewScore;
}

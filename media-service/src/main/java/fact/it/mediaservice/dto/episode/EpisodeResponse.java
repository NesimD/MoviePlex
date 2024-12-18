package fact.it.mediaservice.dto.episode;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class EpisodeResponse {
    private String id;
    private String title;
    private String Description;
    private double duration;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date releaseDate;
    private Rating rating;
}

package fact.it.userservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import fact.it.userservice.model.Episode;
import fact.it.userservice.model.Genre;
import fact.it.userservice.model.Rating;
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
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date releaseDate;
    private Rating rating;
    private int reviewScore;
}

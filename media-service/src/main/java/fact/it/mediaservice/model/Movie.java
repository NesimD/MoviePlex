package fact.it.mediaservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(value = "movie")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Movie {
    private String id;
    private String mediaCode;
    private String title;
    private String description;
    private Director director;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date releaseDate;
    private Genre genre;
    private Rating rating;
    private int reviewScore;
}

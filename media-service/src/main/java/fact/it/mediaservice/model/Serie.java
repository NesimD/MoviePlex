package fact.it.mediaservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(value = "serie")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Serie {
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
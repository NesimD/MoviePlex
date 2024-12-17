package fact.it.mediaservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(value = "episode")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Episode {
    private String id;
    private String title;
    private String Description;
    private double duration;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date releaseDate;
    private Rating rating;
}

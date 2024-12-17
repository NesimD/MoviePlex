package fact.it.userservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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

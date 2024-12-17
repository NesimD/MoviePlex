package fact.it.mediaservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "genre")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Genre {
    private String id;
    private String name;
}

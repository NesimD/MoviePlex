package fact.it.mediaservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "director")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Director {
    private String id;
    private String firstName;
    private String lastName;
}

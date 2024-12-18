package fact.it.mediaservice.dto.director;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectorRequest {
    private String firstName;
    private String lastName;
}

package fact.it.mediaservice;

import fact.it.mediaservice.dto.director.DirectorRequest;
import fact.it.mediaservice.dto.director.DirectorResponse;
import fact.it.mediaservice.model.Director;
import fact.it.mediaservice.repository.DirectorRepository;
import fact.it.mediaservice.service.DirectorService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DirectorServiceTests {
    @InjectMocks
    private DirectorService directorService;

    @Mock
    private DirectorRepository directorRepository;

    @Test
    public void testGetDirectorByIdDirectorFound() {
        // Arrange
        Director director = new Director();
        director.setId("1");
        director.setFirstName("Tom");
        director.setLastName("Jassens");

        when(directorRepository.findById("1")).thenReturn(Optional.of(director));

        // Act
        Optional<DirectorResponse> directorResponseOptional = directorService.getDirectorById("1");

        // Assert
        assertTrue(directorResponseOptional.isPresent());
        assertEquals(director.getId(), directorResponseOptional.get().getId());
        assertEquals(director.getFirstName(), directorResponseOptional.get().getFirstName());
        assertEquals(director.getLastName(), directorResponseOptional.get().getLastName());

        verify(directorRepository, times(1)).findById("1");
    }

    @Test
    public void testGetDirectorByIdDirectorNotFound() {
        // Arrange
        Director director = new Director();
        director.setId("1");
        director.setFirstName("Tom");
        director.setLastName("Jassens");

        when(directorRepository.findById("1")).thenReturn(Optional.of(director));

        // Act
        Optional<DirectorResponse> directorResponseOptional = directorService.getDirectorById("100");

        // Assert
        assertEquals(Optional.empty(), directorResponseOptional);
    }

    @Test
    public void testGetAllDirectors() {
        // Arrange
        Director director1 = new Director();
        director1.setId("1");
        director1.setFirstName("Tom");
        director1.setLastName("Jassens");

        Director director2 = new Director();
        director2.setId("2");
        director2.setFirstName("Jan");
        director2.setLastName("Jassens");


        when(directorRepository.findAll()).thenReturn(Arrays.asList(director1, director2));

        // Act
        List<DirectorResponse> directorResponses = directorService.getAllDirectors();

        // Assert
        assertEquals(2, directorResponses.size());
        assertEquals(director1.getId(), directorResponses.get(0).getId());
        assertEquals(director1.getFirstName(), directorResponses.get(0).getFirstName());
        assertEquals(director1.getLastName(), directorResponses.get(0).getLastName());

        assertEquals(director2.getId(), directorResponses.get(1).getId());
        assertEquals(director2.getFirstName(), directorResponses.get(1).getFirstName());
        assertEquals(director2.getLastName(), directorResponses.get(1).getLastName());

        verify(directorRepository, times(1)).findAll();
    }

    @Test public void testCreateDirector() {
        // Arrange
        Director director = new Director();
        director.setFirstName("Tom");
        director.setLastName("Jassens");

        DirectorRequest directorRequest = new DirectorRequest();
        directorRequest.setFirstName("Tom");
        directorRequest.setLastName("Jassens");

        when(directorRepository.save(Mockito.any(Director.class))).thenReturn(director);

        // Act
        DirectorResponse directorResponse = directorService.createDirector(directorRequest);

        // Assert

        Assertions.assertThat(directorResponse).isNotNull();
        assertEquals(director.getFirstName(), directorResponse.getFirstName());
        assertEquals(director.getLastName(), directorResponse.getLastName());
    }

    @Test public void testDeleteDirector() {
        Director director = new Director();
        director.setFirstName("Tom");
        director.setLastName("Jassens");

        when(directorRepository.findById("1")).thenReturn(Optional.of(director));

        directorService.deleteDirector("1");

        verify(directorRepository, times(1)).deleteById("1");
    }
}

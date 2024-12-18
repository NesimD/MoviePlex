package fact.it.mediaservice;

import fact.it.mediaservice.dto.genre.GenreRequest;
import fact.it.mediaservice.dto.genre.GenreResponse;
import fact.it.mediaservice.model.Genre;
import fact.it.mediaservice.repository.GenreRepository;
import fact.it.mediaservice.service.GenreService;
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
public class GenreServiceTests {
    @InjectMocks
    private GenreService genreService;

    @Mock
    private GenreRepository genreRepository;

    @Test
    public void testGetGenreByIdGenreFound() {
        // Arrange
        Genre genre = new Genre();
        genre.setId("1");
        genre.setName("Horror");

        when(genreRepository.findById("1")).thenReturn(Optional.of(genre));

        // Act
        Optional<GenreResponse> genreResponseOptional = genreService.getGenreById("1");

        // Assert
        assertTrue(genreResponseOptional.isPresent());
        assertEquals(genre.getId(), genreResponseOptional.get().getId());
        assertEquals(genre.getName(), genreResponseOptional.get().getName());

        verify(genreRepository, times(1)).findById("1");
    }

    @Test
    public void testGetGenreByIdGenreNotFound() {
        // Arrange
        Genre genre = new Genre();
        genre.setId("1");
        genre.setName("Horror");

        when(genreRepository.findById("1")).thenReturn(Optional.of(genre));

        // Act
        Optional<GenreResponse> genreResponseOptional = genreService.getGenreById("100");

        // Assert
        assertEquals(Optional.empty(), genreResponseOptional);
    }

    @Test
    public void testGetAllGenres() {
        // Arrange
        Genre genre1 = new Genre();
        genre1.setId("1");
        genre1.setName("Horror");

        Genre genre2 = new Genre();
        genre2.setId("2");
        genre2.setName("Action");


        when(genreRepository.findAll()).thenReturn(Arrays.asList(genre1, genre2));

        // Act
        List<GenreResponse> genreResponses = genreService.getAllGenres();

        // Assert
        assertEquals(2, genreResponses.size());
        assertEquals(genre1.getId(), genreResponses.get(0).getId());
        assertEquals(genre1.getName(), genreResponses.get(0).getName());

        assertEquals(genre2.getId(), genreResponses.get(1).getId());
        assertEquals(genre2.getName(), genreResponses.get(1).getName());

        verify(genreRepository, times(1)).findAll();
    }

    @Test public void testCreateGenre() {
        // Arrange
        Genre genre = new Genre();
        genre.setName("Horror");

        GenreRequest genreRequest = new GenreRequest();
        genreRequest.setName("Horror");

        when(genreRepository.save(Mockito.any(Genre.class))).thenReturn(genre);

        // Act
        GenreResponse genreResponse = genreService.createGenre(genreRequest);

        // Assert

        Assertions.assertThat(genreResponse).isNotNull();
        assertEquals(genre.getId(), genreResponse.getId());
        assertEquals(genre.getName(), genreResponse.getName());
    }

    @Test public void testDeleteGenre() {
        Genre genre = new Genre();
        genre.setId("1");
        genre.setName("Horror");

        when(genreRepository.findById("1")).thenReturn(Optional.of(genre));

        genreService.deleteGenre("1");

        verify(genreRepository, times(1)).deleteById("1");
    }
}

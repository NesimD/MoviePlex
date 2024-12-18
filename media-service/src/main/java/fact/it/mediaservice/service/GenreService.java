package fact.it.mediaservice.service;

import fact.it.mediaservice.dto.director.DirectorRequest;
import fact.it.mediaservice.dto.director.DirectorResponse;
import fact.it.mediaservice.dto.genre.GenreRequest;
import fact.it.mediaservice.dto.genre.GenreResponse;
import fact.it.mediaservice.model.Director;
import fact.it.mediaservice.model.Genre;
import fact.it.mediaservice.repository.DirectorRepository;
import fact.it.mediaservice.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    public Optional<GenreResponse> getGenreById(String id) {
        Optional<Genre> genreOptional = genreRepository.findById(id);

        if (genreOptional.isPresent()) {
            Genre genre = genreOptional.get();
            GenreResponse genreResponse = mapToGenreResponse(genre);

            return Optional.of(genreResponse);
        }
        return Optional.empty();
    }

    public List<GenreResponse> getAllGenres() {
        List<Genre> genres = genreRepository.findAll();

        return genres.stream().map(this::mapToGenreResponse).toList();
    }

    public GenreResponse createGenre(GenreRequest genreRequest){

        Genre genre = Genre.builder()
                .name(genreRequest.getName())
                .build();

        genreRepository.save(genre);
        return mapToGenreResponse(genre);
    }

    public void deleteGenre(String id) {
        genreRepository.deleteById(id);
    }

    private GenreResponse mapToGenreResponse(Genre genre) {
        return GenreResponse.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }
}

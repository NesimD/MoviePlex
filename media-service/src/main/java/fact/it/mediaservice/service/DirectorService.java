package fact.it.mediaservice.service;

import fact.it.mediaservice.dto.director.DirectorRequest;
import fact.it.mediaservice.dto.director.DirectorResponse;
import fact.it.mediaservice.model.Director;
import fact.it.mediaservice.repository.DirectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DirectorService {
    private final DirectorRepository directorRepository;

    public Optional<DirectorResponse> getDirectorById(String id) {
        Optional<Director> directorOptional = directorRepository.findById(id);

        if (directorOptional.isPresent()) {
            Director director = directorOptional.get();
            DirectorResponse directorResponse = mapToDirectorResponse(director);

            return Optional.of(directorResponse);
        }
        return Optional.empty();
    }

    public List<DirectorResponse> getAllDirectors() {
        List<Director> directors = directorRepository.findAll();

        return directors.stream().map(this::mapToDirectorResponse).toList();
    }

    public DirectorResponse createDirector(DirectorRequest directorRequest){

        Director director = Director.builder()
                .firstName(directorRequest.getFirstName())
                .lastName(directorRequest.getLastName())
                .build();

        directorRepository.save(director);
        return mapToDirectorResponse(director);
    }

    public void deleteDirector(String id) {
        directorRepository.deleteById(id);
    }

    private DirectorResponse mapToDirectorResponse(Director director) {
        return DirectorResponse.builder()
                .id(director.getId())
                .firstName(director.getFirstName())
                .lastName(director.getLastName())
                .build();
    }
}

package fact.it.mediaservice.service;

import fact.it.mediaservice.dto.SerieRequest;
import fact.it.mediaservice.dto.SerieResponse;
import fact.it.mediaservice.model.Serie;
import fact.it.mediaservice.repository.SerieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SerieService {
    private final SerieRepository serieRepository;

    public void createSerie(SerieRequest serieRequest){
        Serie serie = Serie.builder()
                .mediaCode(serieRequest.getMediaCode())
                .title(serieRequest.getTitle())
                .genre(serieRequest.getGenre())
                .seasons(serieRequest.getSeasons())
                .episode(serieRequest.getEpisode())
                .releaseDate(serieRequest.getReleaseDate())
                .rating(serieRequest.getRating())
                .reviewScore(serieRequest.getReviewScore())
                .build();

        serieRepository.save(serie);
    }

    public List<SerieResponse> getAllSeries() {
        List<Serie> movies = serieRepository.findAll();

        return movies.stream().map(this::mapToSerieResponse).toList();
    }

    public SerieResponse getSerieByMediaCode(String mediaCode) {
        Serie serie = serieRepository.findSerieByMediaCode(mediaCode);
        return mapToSerieResponse(serie);
    }

    public List<SerieResponse> getSeriesByMediaCodes(List<String> mediaCodes) {
        return serieRepository.findSeriesByMediaCodeIn(mediaCodes).stream()
                .map(this::mapToSerieResponse)
                .toList();
    }

    public SerieResponse updateSerieById(SerieRequest serieRequest, String id) {
        Optional<Serie> serieOptional = serieRepository.findById(id);

        if (serieOptional.isPresent()) {
            Serie serie = serieOptional.get();

            serie.setMediaCode(serieRequest.getMediaCode());
            serie.setTitle(serieRequest.getTitle());
            serie.setGenre(serieRequest.getGenre());
            serie.setSeasons(serieRequest.getSeasons());
            serie.setEpisode(serieRequest.getEpisode());
            serie.setReleaseDate(serieRequest.getReleaseDate());
            serie.setRating(serieRequest.getRating());
            serie.setReviewScore(serieRequest.getReviewScore());
            return mapToSerieResponse(serie);
        }
        return null;
    }

    private SerieResponse mapToSerieResponse(Serie serie) {
        return SerieResponse.builder()
                .id(serie.getId())
                .mediaCode(serie.getMediaCode())
                .title(serie.getTitle())
                .genre(serie.getGenre())
                .seasons(serie.getSeasons())
                .episode(serie.getEpisode())
                .releaseDate(serie.getReleaseDate())
                .rating(serie.getRating())
                .reviewScore(serie.getReviewScore())
                .build();
    }
}

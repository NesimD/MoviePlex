package fact.it.mediaservice.service;

import fact.it.mediaservice.dto.serie.SerieRequest;
import fact.it.mediaservice.dto.serie.SerieResponse;
import fact.it.mediaservice.model.Episode;
import fact.it.mediaservice.model.Genre;
import fact.it.mediaservice.model.Rating;
import fact.it.mediaservice.model.Serie;
import fact.it.mediaservice.repository.EpisodeRepository;
import fact.it.mediaservice.repository.GenreRepository;
import fact.it.mediaservice.repository.RatingRepository;
import fact.it.mediaservice.repository.SerieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SerieService {
    private final SerieRepository serieRepository;
    private final GenreRepository genreRepository;
    private final RatingRepository ratingRepository;
    private final EpisodeRepository episodeRepository;

    public void createSerie(SerieRequest serieRequest){
        Genre genre = genreRepository.findById(serieRequest.getGenreId()).orElse(null);
        Rating rating = ratingRepository.findById(serieRequest.getRatingId()).orElse(null);
        Episode episode = episodeRepository.findById(serieRequest.getEpisodeId()).orElse(null);

        Serie serie = Serie.builder()
                .mediaCode(serieRequest.getMediaCode())
                .title(serieRequest.getTitle())
                .genre(genre)
                .seasons(serieRequest.getSeasons())
                .episode(episode)
                .releaseDate(serieRequest.getReleaseDate())
                .rating(rating)
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
        Genre genre = genreRepository.findById(serieRequest.getGenreId()).orElse(null);
        Rating rating = ratingRepository.findById(serieRequest.getRatingId()).orElse(null);
        Episode episode = episodeRepository.findById(serieRequest.getEpisodeId()).orElse(null);

        if (serieOptional.isPresent()) {
            Serie serie = serieOptional.get();

            serie.setMediaCode(serieRequest.getMediaCode());
            serie.setTitle(serieRequest.getTitle());
            serie.setGenre(genre);
            serie.setSeasons(serieRequest.getSeasons());
            serie.setEpisode(episode);
            serie.setReleaseDate(serieRequest.getReleaseDate());
            serie.setRating(rating);
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

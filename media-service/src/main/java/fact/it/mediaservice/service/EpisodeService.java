package fact.it.mediaservice.service;

import fact.it.mediaservice.dto.episode.EpisodeRequest;
import fact.it.mediaservice.dto.episode.EpisodeResponse;
import fact.it.mediaservice.model.Episode;
import fact.it.mediaservice.model.Rating;
import fact.it.mediaservice.repository.EpisodeRepository;
import fact.it.mediaservice.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EpisodeService {
    private final EpisodeRepository episodeRepository;
    private final RatingRepository ratingRepository;

    public Optional<EpisodeResponse> getEpisodeById(String id) {
        Optional<Episode> episodeOptional = episodeRepository.findById(id);

        if (episodeOptional.isPresent()) {
            Episode episode = episodeOptional.get();
            EpisodeResponse episodeResponse = mapToEpisodeResponse(episode);

            return Optional.of(episodeResponse);
        }
        return Optional.empty();
    }

    public List<EpisodeResponse> getAllEpisodes() {
        List<Episode> episodes = episodeRepository.findAll();

        return episodes.stream().map(this::mapToEpisodeResponse).toList();
    }

    public void createEpisode(EpisodeRequest episodeRequest){
        Rating rating = ratingRepository.findById(episodeRequest.getRatingId()).orElse(null);

        Episode episode = Episode.builder()
                .title(episodeRequest.getTitle())
                .Description(episodeRequest.getDescription())
                .duration(episodeRequest.getDuration())
                .releaseDate(episodeRequest.getReleaseDate())
                .rating(rating)
                .build();

        episodeRepository.save(episode);
    }

    public void deleteEpisode(String id) {
        episodeRepository.deleteById(id);
    }

    private EpisodeResponse mapToEpisodeResponse(Episode episode) {
        return EpisodeResponse.builder()
                .id(episode.getId())
                .title(episode.getTitle())
                .Description(episode.getDescription())
                .duration(episode.getDuration())
                .releaseDate(episode.getReleaseDate())
                .rating(episode.getRating())
                .build();
    }
}

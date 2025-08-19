package Service;

import model.ArtWork;

import java.util.List;
import java.util.Map;

public interface ArtWorkService {
    List<ArtWork> getArtworksAfterYearUnderPrice();
    Double getAveragePrice();
    void PrintTopThreeMostExpensive();
    Map<String, Long> getArtworksCountByArtist();
}

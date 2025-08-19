package repository;

import model.ArtWork;

import java.util.List;
import java.util.Optional;

public interface ArtWorkRepository {
    void save(ArtWork artWork);

    Optional<ArtWork> findById(Long id);

    List<ArtWork> findByArtistName(String artistName);

    List<ArtWork> findAll();

    void updateArtistById(Long id, String name);

    void updatePriceById(Long id, Double price);

    void removeById(Long id);
}

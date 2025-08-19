package Service.impl;

import Service.ArtWorkService;
import model.ArtWork;
import repository.ArtWorkRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ArtWorkServiceImpl implements ArtWorkService {
    private ArtWorkRepository artWorkRepository;
    public ArtWorkServiceImpl (ArtWorkRepository artWorkRepository){
        this.artWorkRepository = artWorkRepository;
    }

    @Override
    public List<ArtWork> getArtworksAfterYearUnderPrice() {
        return artWorkRepository.findAll().stream()
                .filter(artwork -> artwork.getCreationYear() > 2000)
                .filter(artwork -> artwork.getPrice() < 5000.0)
                .collect(Collectors.toList());
    }

    @Override
    public Double getAveragePrice() {
        return artWorkRepository.findAll().stream()
                .mapToDouble(ArtWork::getPrice)
                .average()
                .orElse(0.0);
    }

    @Override
    public void PrintTopThreeMostExpensive() {
        artWorkRepository.findAll().stream()
                .sorted((a1, a2) -> Double.compare(a2.getPrice(), a1.getPrice()))
                .limit(3)
                .forEach(artWork -> {
                    System.out.println("Title: " + artWork.getTitle());
                    System.out.println("Artist: " + artWork.getArtist());
                });
    }

    @Override
    public Map<String, Long> getArtworksCountByArtist() {
        return artWorkRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        ArtWork::getArtist,
                        Collectors.counting()
                ));
    }
}


import Service.ArtWorkService;
import Service.impl.ArtWorkServiceImpl;
import jakarta.persistence.criteria.Order;
import model.ArtWork;
import repository.ArtWorkRepository;
import repository.impl.ArtWorkRepositoryImpl;
import util.EntityManagerProvider;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArtWorkRepository artWorkRepository = new ArtWorkRepositoryImpl(new EntityManagerProvider());

        ArtWorkService artWorkService = new ArtWorkServiceImpl(artWorkRepository);

        while (true) {
            System.out.println("--------------------------------------");
            System.out.println("Art Gallery Inventory Management:");
            System.out.println("1. Add new artwork");
            System.out.println("2. Display all artworks");
            System.out.println("3. Find an artwork by id");
            System.out.println("4. Find artworks by artist name");
            System.out.println("5. Modify price of artwork by Id");
            System.out.println("6. Modify artist of artwork by Id");
            System.out.println("7. Delete an artwork by Id");
            System.out.println("8. Find all artworks created after 2000 with a price less than 5,000 USD");
            System.out.println("9. Calculate the average price of all artworks");
            System.out.println("10. Retrieve the top 3 most expensive artworks and print their title and artist");
            System.out.println("11. Group artworks by artist and print the number of artworks for each artist");
            System.out.println("12. Exit");
            System.out.println("-------------------------------------------");
            System.out.print("Please Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Enter title:");
                    String title = scanner.nextLine();
                    System.out.println("Enter artist:");
                    String artist = scanner.nextLine();
                    System.out.println("Enter creation year:");
                    int creationYear = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter price:");
                    double price = scanner.nextDouble();
                    scanner.nextLine();
                    try {
                        artWorkRepository.save(new ArtWork(title,artist,creationYear,price));
                        System.out.println("artwork added successfully");
                    } catch (RuntimeException exception) {
                        System.out.println(exception.getMessage());
                    }
                    break;
                case "2":
                    try {
                        artWorkRepository.findAll().forEach(System.out::println);
                    } catch (RuntimeException exception) {
                        System.out.println(exception.getMessage());
                    }
                    break;
                case "3":
                    System.out.println("Enter id:");
                    Long findById = scanner.nextLong();
                    scanner.nextLine();
                    try {
                        System.out.println(artWorkRepository.findById(findById));
                    } catch (RuntimeException exception) {
                        System.out.println(exception.getMessage());
                    }
                    break;
                case "4":
                    System.out.println("Enter artist name:");
                    String artistName = scanner.nextLine();
                    try {
                        artWorkRepository.findByArtistName(artistName).forEach(System.out::println);
                    } catch (RuntimeException exception) {
                        System.out.println(exception.getMessage());
                    }
                    break;
                case "5":
                    System.out.println("Enter id:");
                    Long idToModify = scanner.nextLong();
                    scanner.nextLine();
                    System.out.println("Enter price:");
                    Double priceModify = scanner.nextDouble();
                    scanner.nextLine();
                    try {
                        artWorkRepository.updatePriceById(idToModify,priceModify);
                    } catch (RuntimeException exception) {
                        System.out.println(exception.getMessage());
                    }
                    break;
                case "6":
                    System.out.println("Enter id:");
                    Long idModify = scanner.nextLong();
                    scanner.nextLine();
                    System.out.println("Enter artist:");
                    String artistModify = scanner.nextLine();
                    try {
                        artWorkRepository.updateArtistById(idModify,artistModify);
                    } catch (RuntimeException exception) {
                        System.out.println(exception.getMessage());
                    }
                    break;
                case "7":
                    System.out.println("Enter id:");
                    Long idToRemove = scanner.nextLong();
                    scanner.nextLine();
                    try {
                        artWorkRepository.removeById(idToRemove);
                    } catch (RuntimeException exception) {
                        System.out.println(exception.getMessage());
                    }
                    break;
                case "8":
                    artWorkService.getArtworksAfterYearUnderPrice()
                            .forEach(System.out::println);
                    break;
                case "9":
                    System.out.println("Average Price: "+ artWorkService.getAveragePrice());
                    break;
                case "10":
                    artWorkService.PrintTopThreeMostExpensive();
                    break;
                case "11":
                    artWorkService.getArtworksCountByArtist()
                            .entrySet()
                            .forEach(entry ->
                                    System.out.println(entry.getKey() + " ->" + entry.getValue()));
                    break;
                case "12":
                    System.out.println("Exit the Artwork management!");
                    return;
                default:
                    System.out.println("Please enter choice between 1 to 12!");
            }

        }


    }
}

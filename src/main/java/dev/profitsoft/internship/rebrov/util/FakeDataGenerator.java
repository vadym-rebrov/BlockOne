package dev.profitsoft.internship.rebrov.util;

import com.github.javafaker.Faker;
import dev.profitsoft.internship.rebrov.model.Director;
import dev.profitsoft.internship.rebrov.model.Movie;
import dev.profitsoft.internship.rebrov.parser.JsonParser;

import java.util.*;

/*
* Generating fake data for dataset using Faker library
*/
public class FakeDataGenerator {

    public static final int NUMBER_OF_FILES = 10;


    public static void main(String[] args) {
        int k  = NUMBER_OF_FILES;
        for(int i = 1; i<= NUMBER_OF_FILES;i++){
            generateFakeMoviesJson(k, ("./src/main/resources/dataset/fake_movies_"+i+".json"));
        }
        System.out.println("Total files: " + NUMBER_OF_FILES + ". Total movies: " + k*NUMBER_OF_FILES + ".");
    }

    private static final Faker FAKER = new Faker();
    private static final String[] commonAwards = new String[]{
            "BAFTA", "Oscar", "Palme d’Or", "Golden Globe Award"
    };

    public static void generateFakeMoviesJson(int numberOfMovies, String filename) {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < numberOfMovies; i++) {
            movies.add(generateFakeMovie());
        }

        JsonParser<Movie> parser = new JsonParser<>(Movie.class);
        parser.writeFile(movies, filename);
    }

    public static Movie generateFakeMovie() {
        String title = FAKER.book().title();
        int releaseYear = FAKER.number().numberBetween(1950, 2023);
        int randomGenresQuantity = FAKER.number().numberBetween(1, 5);
        StringBuilder genres = new StringBuilder();
        for (int i = 0; i < randomGenresQuantity; i++){
            genres.append(FAKER.book().genre());
            if(i < randomGenresQuantity - 1){
                genres.append(", ");
            }
        }
        double rating = FAKER.number().randomDouble(1, 1, 10);
        Director director = generateFakeDirector();
        List<String> awards = generateFakeAwards(FAKER.number().numberBetween(1, commonAwards.length));
        return new Movie(title, releaseYear, genres.toString(), rating, director, awards);
    }

    public static Director generateFakeDirector() {
        String fullName = FAKER.name().fullName();
        String country = FAKER.address().country();
        int birthYear = FAKER.number().numberBetween(1930, 2000);
        return new Director(fullName, country, birthYear);
    }

    public static List<String> generateFakeAwards(int numberOfAwards) {
        List<String> awards = new ArrayList<>();
        List<String> pool = new ArrayList<>(Arrays.asList(commonAwards));
        Collections.shuffle(pool);
        int limit = Math.min(numberOfAwards, pool.size());
        for (int i = 0; i < limit; i++) {
            awards.add((pool.get(i)));
        }

        return awards;
    }

}

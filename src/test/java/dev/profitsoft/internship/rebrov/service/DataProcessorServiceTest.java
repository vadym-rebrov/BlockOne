package dev.profitsoft.internship.rebrov.service;

import dev.profitsoft.internship.rebrov.model.Director;
import dev.profitsoft.internship.rebrov.model.Movie;
import dev.profitsoft.internship.rebrov.service.DataProcessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DataProcessorServiceTest {
    private DataProcessorService<Movie> processor;

    @BeforeEach
    void setUp() {
        processor = new DataProcessorService<>();
    }

    @Test
    void testNestedFieldCount() {
        Director director = new Director("John Doe", "USA", 1980);
        Movie movie = new Movie();
        movie.setDirector(director);

        Map<String, Integer> result = processor.countAttributeValuesByItem(movie, "director.country");

        assertEquals(1, result.get("USA"));
    }

    @Test
    void testCommaSeparatedStringCount() {
        Movie movie = new Movie();
        movie.setGenres("Drama, Action, Sci-Fi"); //

        Map<String, Integer> result = processor.countAttributeValuesByItem(movie, "genres");

        assertEquals(3, result.size());
        assertEquals(1, result.get("Drama"));
        assertEquals(1, result.get("Action"));
        assertEquals(1, result.get("Sci-Fi"));
    }

    @Test
    void testListAttributeCount() {
        Movie movie = new Movie();
        movie.setAwards(List.of("Oscar", "BAFTA"));

        Map<String, Integer> result = processor.countAttributeValuesByItem(movie, "awards");

        assertEquals(2, result.size());
        assertEquals(1, result.get("Oscar"));
        assertEquals(1, result.get("BAFTA"));
    }

    @Test
    void testInvalidAttributeThrowsException() {
        Movie movie = new Movie();
        assertThrows(IllegalArgumentException.class, () ->
                processor.countAttributeValuesByItem(movie, "invalid field name with spaces")
        );
    }
}

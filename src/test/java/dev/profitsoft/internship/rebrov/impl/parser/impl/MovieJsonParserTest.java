package dev.profitsoft.internship.rebrov.impl.parser.impl;

import dev.profitsoft.internship.rebrov.model.Movie;
import dev.profitsoft.internship.rebrov.parser.impl.MovieJsonParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovieJsonParserTest {
    @TempDir
    Path tempDir;

    @Test
    void testReadAndProcess() throws IOException {
        File jsonFile = tempDir.resolve("test_movies.json").toFile();

        String jsonContent = """
            [
              {
                "title": "Inception",
                "releaseYear": 2010,
                "genres": "Sci-Fi, Action",
                "rating": 8.8,
                "director": {
                  "fullName": "Christopher Nolan",
                  "country": "USA",
                  "birthYear": 1970
                },
                "awards": ["Oscar", "BAFTA"]
              },
              {
                "title": "Interstellar",
                "releaseYear": 2014,
                "genres": "Sci-Fi, Drama",
                "rating": 8.6,
                "director": {
                  "fullName": "Christopher Nolan",
                  "country": "USA",
                  "birthYear": 1970
                },
                "awards": ["Oscar"]
              }
            ]
            """;

        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write(jsonContent);
        }

        MovieJsonParser parser = new MovieJsonParser();
        List<Movie> results = Collections.synchronizedList(new LinkedList<>());

        parser.readAndProcess(tempDir.toString(), 1, results::add);

        assertEquals(2, results.size(), "Should read 2 movies");

        Movie movie1 = results.stream().filter(m -> m.getTitle().equals("Inception")).findFirst().orElseThrow();
        assertEquals(2010, movie1.getReleaseYear());
        assertEquals("Christopher Nolan", movie1.getDirector().getFullName());
        assertTrue(movie1.getAwards().contains("BAFTA"));
    }

    @Test
    void testReadInvalidDirectory() {
        MovieJsonParser parser = new MovieJsonParser();
        assertThrows(IllegalArgumentException.class, () ->
                parser.readAndProcess("fake/path/<>", 1, m -> {})
        );
    }
}

package dev.profitsoft.internship.rebrov.impl.service.impl;

import dev.profitsoft.internship.rebrov.model.Director;
import dev.profitsoft.internship.rebrov.model.Movie;
import dev.profitsoft.internship.rebrov.model.Statistics;
import dev.profitsoft.internship.rebrov.parser.impl.MovieJsonParser;
import dev.profitsoft.internship.rebrov.service.DataProcessorService;
import dev.profitsoft.internship.rebrov.service.impl.JsonMovieStatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
public class JsonMovieStatisticsServiceTest {
    @Mock
    private MovieJsonParser parser;

    @Spy
    private DataProcessorService<Movie> processor;

    private JsonMovieStatisticsService statisticsService;

    @BeforeEach
    void setUp() {
        statisticsService = new JsonMovieStatisticsService(parser, processor);
    }

    @Test
    void testGetAttrStatisticsByDirectory() {
        Movie movie1 = new Movie();
        movie1.setDirector(new Director("Nolan", "USA", 1970));
        Movie movie2 = new Movie();
        movie2.setDirector(new Director("Nolan", "USA", 1970));
        Movie movie3 = new Movie();
        movie3.setDirector(new Director("John Woo", "China", 1967));

        doAnswer(invocation -> {
            Consumer<Movie> consumer = invocation.getArgument(2);
            consumer.accept(movie1);
            consumer.accept(movie2);
            consumer.accept(movie3);
            return null;
        }).when(parser).readAndProcess(anyString(), anyInt(), any(Consumer.class));

        Statistics stats = statisticsService.getAttrStatisticsByDirectory("fake/path", "director.fullName", 1);

        assertNotNull(stats);
        List<Statistics.StatisticsItem> items = stats.getItems();

        assertEquals(2, items.size());

        // Sort check
        assertEquals("Nolan", items.get(0).getValue());
        assertEquals(2, items.get(0).getCount());

        assertEquals("John Woo", items.get(1).getValue());
        assertEquals(1, items.get(1).getCount());
    }

    @Test
    void testInvalidAttribute() {
        assertThrows(IllegalArgumentException.class, () ->
                statisticsService.getAttrStatisticsByDirectory("path", "Fake Attribute Name", 1)
        );
    }
}


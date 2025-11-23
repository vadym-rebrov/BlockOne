package dev.profitsoft.internship.rebrov.service.impl;

import dev.profitsoft.internship.rebrov.model.Movie;
import dev.profitsoft.internship.rebrov.model.Statistics;
import dev.profitsoft.internship.rebrov.parser.impl.MovieJsonParser;
import dev.profitsoft.internship.rebrov.service.DataProcessorService;
import dev.profitsoft.internship.rebrov.service.StatisticsService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static dev.profitsoft.internship.rebrov.model.constant.Constant.MAX_THREAD_COUNT;
import static dev.profitsoft.internship.rebrov.validation.CustomValidator.isClassAttribute;

public class JsonMovieStatisticsService implements StatisticsService {

    private MovieJsonParser parser;
    private DataProcessorService<Movie> processor;

    public JsonMovieStatisticsService(MovieJsonParser parser, DataProcessorService<Movie> processor){
        this.parser = parser;
        this.processor = processor;
    }

    @Override
    public Statistics getAttrStatisticsByDirectory(String directoryPath, String attributeName, Integer threadCount) {
        if(!isClassAttribute(attributeName)){
            throw new IllegalArgumentException("Invalid attribute name");
        }
        if(threadCount == null || threadCount < 1){
            threadCount = MAX_THREAD_COUNT;
        }
        ConcurrentHashMap<String, Integer> stats = new ConcurrentHashMap<>();

        parser.readAndProcess(directoryPath, threadCount, (movie) -> {
            Map<String, Integer> chunkStats = processor.countAttributeValuesByItem(movie, attributeName);
            chunkStats.forEach((key, count) ->
                    stats.merge(key, count, Integer::sum)
            );
        });

        List<Statistics.StatisticsItem> items = stats.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(e -> new Statistics.StatisticsItem(e.getKey(), e.getValue()))
                .toList();

        return !items.isEmpty() ? new Statistics(items) : null;
    }

    public MovieJsonParser getParser() {
        return parser;
    }

    public void setParser(MovieJsonParser parser) {
        this.parser = parser;
    }

    public DataProcessorService<Movie> getProcessor() {
        return processor;
    }

    public void setProcessor(DataProcessorService<Movie> processor) {
        this.processor = processor;
    }
}

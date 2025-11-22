package dev.profitsoft.internship.rebrov;

import dev.profitsoft.internship.rebrov.model.Movie;
import dev.profitsoft.internship.rebrov.parser.impl.MovieJsonParser;
import dev.profitsoft.internship.rebrov.parser.impl.StatisticsXMLParser;
import dev.profitsoft.internship.rebrov.service.DataProcessorService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class App {


    public static final int MIN_ARGS = 2;
    public static final int MAX_ARGS = 3;


    public static void main(String[] args) {
        try {
            if(args.length < MIN_ARGS || args.length > MAX_ARGS){
                throw new IllegalArgumentException("Wrong number of arguments. Argument 1 - Directory path. Argument 2 - Attribute Name. Argument 3 - Threads count(optional)");
            }

            String folderPath = args[0];
            String attributeName = args[1];

            Integer threadsCount = args.length==MAX_ARGS ? Integer.valueOf(args[2]) : null;
            MovieJsonParser parser = new MovieJsonParser();
            DataProcessorService<Movie> processor = new DataProcessorService<>();
            StatisticsXMLParser statisticsXmlParser = new StatisticsXMLParser();

            Long start = System.currentTimeMillis();
            ConcurrentHashMap<String, Integer> totalStats = new ConcurrentHashMap<>();

            parser.readAndProcess(folderPath, threadsCount, (movieChunk) -> {
                Map<String, Integer> chunkStats = processor.countByItem(movieChunk, attributeName);
                chunkStats.forEach((key, count) ->
                        totalStats.merge(key, count, Integer::sum)
                );
            });
            String statisticsOutput = folderPath + "/stats_by_" + attributeName + ".xml";
            Long end = System.currentTimeMillis();
            System.out.println("Stats processed in " + (end - start) + "ms");
            statisticsXmlParser.writeStatisticsToXml(totalStats, statisticsOutput);


        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }


}

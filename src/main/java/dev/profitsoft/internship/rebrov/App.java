package dev.profitsoft.internship.rebrov;

import dev.profitsoft.internship.rebrov.model.Movie;
import dev.profitsoft.internship.rebrov.model.Statistics;
import dev.profitsoft.internship.rebrov.parser.impl.MovieJsonParser;
import dev.profitsoft.internship.rebrov.parser.impl.StatisticsXMLParser;
import dev.profitsoft.internship.rebrov.service.DataProcessorService;
import dev.profitsoft.internship.rebrov.service.StatisticsService;
import dev.profitsoft.internship.rebrov.service.impl.JsonMovieStatisticsService;

public class App {


    public static final int MIN_ARGS = 2;
    public static final int MAX_ARGS = 3;


    public static void main(String[] args) {
        try {
            if(args.length < MIN_ARGS || args.length > MAX_ARGS){
                throw new IllegalArgumentException("Wrong number of arguments. Argument 1 - Directory path. Argument 2 - Attribute Name. Argument 3 - Threads count(optional)");
            }

            String directoryPath = args[0];
            String attributeName = args[1];

            Integer threadsCount = args.length==MAX_ARGS ? Integer.valueOf(args[2]) : null;
            MovieJsonParser parser = new MovieJsonParser();
            DataProcessorService<Movie> processor = new DataProcessorService<>();
            StatisticsXMLParser statisticsXmlParser = new StatisticsXMLParser();
            StatisticsService statisticsService = new JsonMovieStatisticsService(parser, processor);
            Long start = System.currentTimeMillis();
            Statistics stats = statisticsService.getAttrStatisticsByDirectory(directoryPath, attributeName, threadsCount);
            if (stats == null){
                System.exit(0);
            }
            String statisticsOutputPath = directoryPath + "/stats_by_" + attributeName + ".xml";
            statisticsXmlParser.writeObjToFile(stats, statisticsOutputPath);
            Long end = System.currentTimeMillis();
            System.out.println("Stats processed in " + (end - start) + "ms");

        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }


}

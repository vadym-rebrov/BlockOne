package dev.profitsoft.internship.rebrov.parser.impl;

import dev.profitsoft.internship.rebrov.model.Statistics;
import dev.profitsoft.internship.rebrov.parser.XMLParser;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticsXMLParser extends XMLParser<Statistics> {

    public void writeStatisticsToXml(Map<String, Integer> stats, String filename) {

        List<Statistics.StatisticsItem> items = stats.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(e -> new Statistics.StatisticsItem(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        Statistics wrapper = new Statistics(items);
        writeObjToFile(wrapper, filename);
    }
}

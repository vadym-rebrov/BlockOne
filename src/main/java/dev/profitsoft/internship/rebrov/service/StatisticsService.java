package dev.profitsoft.internship.rebrov.service;

import dev.profitsoft.internship.rebrov.model.Statistics;

public interface StatisticsService {
    
    
    Statistics getAttrStatisticsByDirectory(String directoryPath, String attributeName, Integer threadCount);
}

package dev.profitsoft.internship.rebrov.parser;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.profitsoft.internship.rebrov.model.constant.Constant;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static dev.profitsoft.internship.rebrov.validation.CustomValidator.isDirectoryPath;

public class JsonParser<T> {

    private final ObjectMapper mapper = new ObjectMapper();
    private final Class<T> type;

    public JsonParser(Class<T> type) {
        this.type = type;
    }

    /*
        A universal method that transmits data to the consumer in the form of a pipeline
        without loading the entire JSON file into memory using an iterator.
     */
    public void readAndProcess(String directoryPath, int threadCount, Consumer<T> objectProcessor) {
        if (!isDirectoryPath(directoryPath)) {
            throw new IllegalArgumentException("Invalid directory path");
        }

        File folder = new File(directoryPath);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));

        if (files == null || files.length == 0) {
            System.out.println("No JSON files found in " + directoryPath);
            return;
        }

        int threads = (threadCount > 0)
                ? Math.min(Constant.MAX_THREAD_COUNT, threadCount)
                : Constant.MAX_THREAD_COUNT;

        try (ExecutorService executor = Executors.newFixedThreadPool(threads)) {

            for (File file : files) {
                executor.submit(() -> {
                    try (MappingIterator<T> iterator = mapper.readerFor(type).readValues(file)) {
                        while (iterator.hasNext()) {
                            T obj = iterator.next();
                            objectProcessor.accept(obj);
                        }
                    } catch (Exception e) {
                        System.err.println("Error processing file " + file.getName() + ": " + e.getMessage());
                    }
                });
            }
            executor.shutdown();
            try {
                boolean finished = executor.awaitTermination(10, TimeUnit.MINUTES);
                if (!finished){
                    System.err.println("Timeout: Not all files were processed.");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException("Interrupted while waiting for parsing", e);
            }
        }
    }

    public void writeFile(List<T> objList, String filename) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), objList);
        } catch (IOException e) {
            System.err.println("Error writing file " + filename + ": " + e.getMessage());
        }
    }
}
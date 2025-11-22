package dev.profitsoft.internship.rebrov.parser;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;

public class XMLParser<T> {
    private final XmlMapper xmlMapper = new XmlMapper();

    public void writeObjToFile(T obj, String filename){
        try {
            xmlMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), obj);
        } catch (IOException e) {
            throw new RuntimeException("Error writing XML statistics file", e);
        }
    }
}

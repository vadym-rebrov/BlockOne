package dev.profitsoft.internship.rebrov.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "statistics")
public class Statistics {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "item")
    private List<StatisticsItem> items;

    public static class StatisticsItem {

        @JacksonXmlProperty(localName = "value")
        private String value;

        @JacksonXmlProperty(localName = "count")
        private int count;

        public StatisticsItem() {}

        public StatisticsItem(String value, int count) {
            this.value = value;
            this.count = count;
        }

        public String getValue() {
            return value;
        }

        public int getCount() {
            return count;
        }
    }

    public Statistics() {}

    public Statistics(List<StatisticsItem> items) {
        this.items = items;
    }

    public List<StatisticsItem> getItems() {
        return items;
    }

    public void setItems(List<StatisticsItem> items) {
        this.items = items;
    }
}

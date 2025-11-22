package dev.profitsoft.internship.rebrov.parser.impl;

import dev.profitsoft.internship.rebrov.model.Movie;
import dev.profitsoft.internship.rebrov.parser.JsonParser;

public class MovieJsonParser extends JsonParser<Movie> {
    public MovieJsonParser() {
        super(Movie.class);
    }
}

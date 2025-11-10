package dev.profitsoft.internship.rebrov.model;

import java.util.List;
import java.util.Objects;

public class Movie {
    private String title;
    private int releaseYear;
    private List<String> genres;
    private double rating;
    private Director director;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Movie movie)) return false;
        return releaseYear == movie.releaseYear && Double.compare(rating, movie.rating) == 0 && Objects.equals(title, movie.title) && Objects.equals(genres, movie.genres) && Objects.equals(director, movie.director);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, releaseYear, genres, rating, director);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }
}

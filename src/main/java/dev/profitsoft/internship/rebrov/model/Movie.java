package dev.profitsoft.internship.rebrov.model;
import java.util.List;
import java.util.Objects;

public class Movie {
    private String title;
    private Integer releaseYear;
    private String genres;
    private Double rating;
    private Director director;
    private List<String> awards;

    public Movie(String title, Integer releaseYear, String genres, Double rating, Director director, List<String> awards) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.genres = genres;
        this.rating = rating;
        this.director = director;
        this.awards = awards;
    }

    public Movie() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }


    public List<String> getAwards() {
        return awards;
    }

    public void setAwards(List<String> awards) {
        this.awards = awards;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Movie movie)) return false;
        return Objects.equals(title, movie.title) && Objects.equals(releaseYear, movie.releaseYear) && Objects.equals(genres, movie.genres) && Objects.equals(rating, movie.rating) && Objects.equals(director, movie.director) && Objects.equals(awards, movie.awards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, releaseYear, genres, rating, director, awards);
    }
}


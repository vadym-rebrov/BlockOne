package dev.profitsoft.internship.rebrov.model;

import java.util.Objects;

public class Director {
    private String fullName;
    private String country;
    private Integer birthYear;

    public Director(String fullName, String country, int birthYear) {
        this.fullName = fullName;
        this.country = country;
        this.birthYear = birthYear;
    }

    public Director(){}

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Director director)) return false;
        return Objects.equals(birthYear, director.birthYear) && Objects.equals(fullName, director.fullName) && Objects.equals(country, director.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, country, birthYear);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    @Override
    public String toString() {
        return "{" +
                "fullName='" + fullName + '\'' +
                ", country='" + country + '\'' +
                ", birthYear=" + birthYear +
                '}';
    }
}

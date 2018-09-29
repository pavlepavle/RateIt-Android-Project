package rs.fon.rateit.model;

/**
 * Created by pavlepavle on 18-Jun-17.
 */

public class Movie {

    int movieID;
    String title;
    int year;

    public Movie() {
    }

    public Movie(String title, int year) {
        this.title = title;
        this.year = year;
    }

    public Movie(int movieID, String title, int year) {
        this.movieID = movieID;
        this.title = title;
        this.year = year;
    }

    public int getMovieID() {
        return movieID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return title + " - " + year;
    }
}

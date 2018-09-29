package rs.fon.rateit.model;

/**
 * Created by pavlepavle on 18-Jun-17.
 */

public class Rating {

    Movie movie;
    User user;
    int ocena;

    public Rating() {
    }

    public Rating(Movie movie, User user, int ocena) {
        this.movie = movie;
        this.user = user;
        this.ocena = ocena;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    @Override
    public String toString() {
        return movie.getTitle() +" - " + user.getUsername() +" - " + ocena;
    }
}

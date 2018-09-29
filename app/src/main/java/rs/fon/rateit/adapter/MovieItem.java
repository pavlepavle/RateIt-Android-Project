package rs.fon.rateit.adapter;

import rs.fon.rateit.R;

/**
 * Created by pavlepavle on 19-Jun-17.
 */

public class MovieItem {
    public int resIdThumbnail;
    public String movieName;
    public String movieYear;

    public MovieItem(String countryName, String movieYear) {
        this.resIdThumbnail = R.drawable.ic;
        this.movieName = countryName;
        this.movieYear = movieYear;
    }
}

package rs.fon.rateit.adapter;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by pavlepavle on 19-Jun-17.
 */

public class DataHolder
{
    ImageView ivFlag;
    TextView tvMovieName;
    TextView tvMovieYear;


    public DataHolder() {
    }

    public ImageView getIvFlag() {
        return ivFlag;
    }

    public void setIvFlag(ImageView ivFlag) {
        this.ivFlag = ivFlag;
    }

    public TextView getTvMovieName() {
        return tvMovieName;
    }

    public void setTvMovieName(TextView tvMovieName) {
        this.tvMovieName = tvMovieName;
    }

    public TextView getTvMovieYear() {
        return tvMovieYear;
    }

    public void setTvMovieYear(TextView tvMovieYear) {
        this.tvMovieYear = tvMovieYear;
    }
}

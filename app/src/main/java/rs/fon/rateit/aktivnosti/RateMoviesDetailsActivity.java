package rs.fon.rateit.aktivnosti;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import rs.fon.rateit.R;
import rs.fon.rateit.db.DBBroker;
import rs.fon.rateit.model.Movie;
import rs.fon.rateit.model.Rating;
import rs.fon.rateit.model.User;

public class RateMoviesDetailsActivity extends AppCompatActivity {

    ImageView imageView;
    TextView tvMovieTitle;
    TextView tvMovieYear;
    RatingBar ratingBar;
    Button btnRate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_movies_details);

        imageView = (ImageView) findViewById(R.id.ivMovie23);
        tvMovieTitle = (TextView) findViewById(R.id.tvMovieTitle23);
        tvMovieYear = (TextView) findViewById(R.id.tvMovieYear23);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        btnRate = (Button) findViewById(R.id.btnRate);

        tvMovieTitle.setText(getIntent().getStringExtra("Title"));
        tvMovieYear.setText("("+getIntent().getStringExtra("Year")+")");
        imageView.setImageResource(getIntent().getIntExtra("Image", R.drawable.ic));
        ratingBar.setStepSize(1);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

        int i = DBBroker.vratiInstancu(RateMoviesDetailsActivity.this).getRating(getIntent().getStringExtra("Username"), getIntent().getStringExtra("Title"), getIntent().getStringExtra("Year"));
        if (i>0){
            ratingBar.setRating(i);
        }

        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ocena = (int)ratingBar.getRating();
                if (ocena>0) {
                    String imeFilma = (String) tvMovieTitle.getText();
                    String godinaFilma = getIntent().getStringExtra("Year");

                    Movie m = DBBroker.vratiInstancu(RateMoviesDetailsActivity.this).getMovie(imeFilma, godinaFilma);
                    User u = new User();
                    u.setUsername(getIntent().getStringExtra("Username"));
                    Rating r = new Rating(m, u, ocena);
                    Boolean ocenjen = DBBroker.vratiInstancu(RateMoviesDetailsActivity.this).insertRating(r);
                    if (ocenjen) {
                        Toast.makeText(RateMoviesDetailsActivity.this, "Movie successfully rated!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(RateMoviesDetailsActivity.this, "Error. Movie is not rated!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(RateMoviesDetailsActivity.this, "Choose your rating first!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}

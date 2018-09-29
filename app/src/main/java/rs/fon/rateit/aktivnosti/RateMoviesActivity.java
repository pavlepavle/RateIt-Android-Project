package rs.fon.rateit.aktivnosti;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RatingBar;

import java.util.ArrayList;
import java.util.List;

import rs.fon.rateit.R;
import rs.fon.rateit.adapter.MovieItem;
import rs.fon.rateit.adapter.MovieAdapter;
import rs.fon.rateit.db.DBBroker;

public class RateMoviesActivity extends AppCompatActivity {

    List<MovieItem> lstData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_movies);


        try {
            lstData = new ArrayList<>();
            lstData = DBBroker.vratiInstancu(RateMoviesActivity.this).getAllMoviesData();

            final String username = getIntent().getStringExtra("username");

            ListView listView = (ListView) findViewById(R.id.listViewMovies);


            MovieAdapter adapter = new MovieAdapter(this, R.layout.itemrow, lstData);

            listView.setAdapter(adapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = new Intent();
                    intent.putExtra("Title", lstData.get(position).movieName);
                    intent.putExtra("Year", lstData.get(position).movieYear);
                    intent.putExtra("Image", lstData.get(position).resIdThumbnail);
                    intent.putExtra("Username", username);
                    intent.setClass(RateMoviesActivity.this, RateMoviesDetailsActivity.class);
                    startActivity(intent);
                }
            });


        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

package rs.fon.rateit.aktivnosti;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import rs.fon.rateit.R;

public class UserActivity extends AppCompatActivity {

    int brojac=0;
    String username;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        final TextView tvWelcomeMsg = (TextView) findViewById(R.id.tvWelcomeMsg);
        final Button btnLogout = (Button) findViewById(R.id.btnLogout);
        final Button btnRateMovies = (Button) findViewById(R.id.btnRateMovies);
        final Button btnUpdate = (Button) findViewById(R.id.btnEditProfile);
        final Button btnYourRatings = (Button) findViewById(R.id.btnYourRatings);
        final Button btnTop10 = (Button) findViewById(R.id.btnTop10);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        tvWelcomeMsg.setText(username);


        btnTop10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, Top10MoviesActivity.class);
                UserActivity.this.startActivity(intent);
            }
        });

        btnYourRatings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, YourRatingsActivity.class);
                intent.putExtra("username", username);
                UserActivity.this.startActivity(intent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, EditProfileActivity.class);
                intent.putExtra("username", username);
                UserActivity.this.startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(UserActivity.this, LoginActivity.class);
                UserActivity.this.startActivity(registerIntent);
                finish();
            }
        });

        btnRateMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, RateMoviesActivity.class);
                intent.putExtra("username", username);
                UserActivity.this.startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (brojac==0) {
            Toast.makeText(UserActivity.this, "Press back again to exit application", Toast.LENGTH_LONG).show();
            brojac++;
        } else {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }
}

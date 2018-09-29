package rs.fon.rateit.aktivnosti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import rs.fon.rateit.R;
import rs.fon.rateit.db.DBBroker;
import rs.fon.rateit.db.StorageDbHelper;
import rs.fon.rateit.model.Movie;
import rs.fon.rateit.model.User;

public class UserAdminActivity extends AppCompatActivity {

    StorageDbHelper db;
    int brojac=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_admin);

        db = new StorageDbHelper(this);

        final EditText txtTitleAdmin = (EditText) findViewById(R.id.txtTitleAdmin);
        final EditText txtYearAdmin = (EditText) findViewById(R.id.txtYearAdmin);
        final Button btnAddMovieAdmin = (Button) findViewById(R.id.btnAddMovieAdmin);
        final Button btnIzloguj = (Button) findViewById(R.id.btnIzlogujAdmina);

        btnAddMovieAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = String.valueOf(txtTitleAdmin.getText());
                int year = Integer.valueOf(String.valueOf(txtYearAdmin.getText()));
                Movie m1 = new Movie(title,year);
                boolean isInserted = DBBroker.vratiInstancu(UserAdminActivity.this).insertMovie(m1);
                if (isInserted){
                    Toast.makeText(UserAdminActivity.this,"Movie is added!",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(UserAdminActivity.this,"Error. Movie is not added!",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnIzloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(UserAdminActivity.this, LoginActivity.class);
                UserAdminActivity.this.startActivity(registerIntent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (brojac==0) {
            Toast.makeText(UserAdminActivity.this, "Press back again to exit application", Toast.LENGTH_LONG).show();
            brojac++;
        } else {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }
}

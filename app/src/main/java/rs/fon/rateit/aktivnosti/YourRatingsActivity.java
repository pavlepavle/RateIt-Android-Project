package rs.fon.rateit.aktivnosti;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import rs.fon.rateit.R;
import rs.fon.rateit.db.DBBroker;

public class YourRatingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_ratings);
        final String username = getIntent().getStringExtra("username");
        final ListView listVju = (ListView) findViewById(R.id.listVju);

        List<String> lista = DBBroker.vratiInstancu(YourRatingsActivity.this).getUserRatedMovies(username);
        if (lista.isEmpty()) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(YourRatingsActivity.this);
            alertDialogBuilder
                    .setMessage("You have not rated any movies yet!")
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    finish();
                                }
                            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else {


            ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lista);
            listVju.setAdapter(ad);

        }
    }
}

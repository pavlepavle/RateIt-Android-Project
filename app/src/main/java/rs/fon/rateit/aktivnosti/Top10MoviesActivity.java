package rs.fon.rateit.aktivnosti;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import rs.fon.rateit.R;
import rs.fon.rateit.db.DBBroker;

public class Top10MoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top10_movies);


        final ListView listVju = (ListView) findViewById(R.id.listVju23);

        List<String> lista = DBBroker.vratiInstancu(Top10MoviesActivity.this).getTop10Movies();
        if (lista.isEmpty()){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Top10MoviesActivity.this);
            alertDialogBuilder
                    .setMessage("There are no rated movies yet!")
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

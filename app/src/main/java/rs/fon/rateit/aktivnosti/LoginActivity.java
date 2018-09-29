package rs.fon.rateit.aktivnosti;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import rs.fon.rateit.R;
import rs.fon.rateit.db.DBBroker;
import rs.fon.rateit.db.StorageDbHelper;
import rs.fon.rateit.model.Movie;
import rs.fon.rateit.model.User;


public class LoginActivity extends AppCompatActivity {

    StorageDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new StorageDbHelper(this);
        final EditText txtUsernameLogin = (EditText) findViewById(R.id.txtUsernameLogin);
        final EditText txtPasswordLogin = (EditText) findViewById(R.id.txtPasswordLogin);
        final Button btnLogin = (Button) findViewById(R.id.btnLogin);
        final TextView tvRegisterHere = (TextView) findViewById(R.id.tvRegisterHere);





        //nakon registrovanja username i password popunjeni
        Intent intent = getIntent();
        String usernameR = intent.getStringExtra("username");
        String passwordR = intent.getStringExtra("password");
        txtUsernameLogin.setText(usernameR);
        txtPasswordLogin.setText(passwordR);


        tvRegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtUsernameLogin.setText("");
                txtPasswordLogin.setText("");
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);

            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUsernameLogin.getText().toString();
                String password = txtPasswordLogin.getText().toString();

                User u = new User();
                u.setUsername(username);
                u.setPassword(password);

                int login = DBBroker.vratiInstancu(LoginActivity.this).userLoginn(u);
                Intent userIntent;

                switch (login){
                    case 2:
                        userIntent = new Intent(LoginActivity.this, UserAdminActivity.class);
                        userIntent.putExtra("username", username);
                        LoginActivity.this.startActivity(userIntent);
                        finish();
                        break;
                    case 1:
                        userIntent = new Intent(LoginActivity.this, UserActivity.class);
                        userIntent.putExtra("username", username);
                        LoginActivity.this.startActivity(userIntent);
                        finish();
                        break;
                    case 0:
                        Toast.makeText(LoginActivity.this, "Username or password is incorrect", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(LoginActivity.this, "Error!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
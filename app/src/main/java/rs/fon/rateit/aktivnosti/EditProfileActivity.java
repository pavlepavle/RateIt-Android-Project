package rs.fon.rateit.aktivnosti;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rs.fon.rateit.R;
import rs.fon.rateit.db.DBBroker;
import rs.fon.rateit.db.StorageDbHelper;
import rs.fon.rateit.model.User;

public class EditProfileActivity extends AppCompatActivity {

    public Pattern emailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        final EditText txtUsernameEdit = (EditText) findViewById(R.id.txtUsernameEditProfile);
        final EditText txtEmailEdit = (EditText) findViewById(R.id.txtEmailEditProfile);
        final EditText txtPasswordEdit = (EditText) findViewById(R.id.txtPasswordEditProfile);
        final Button btnUpdate = (Button) findViewById(R.id.btnUpdateProfile);
        final Button btnDelete = (Button) findViewById(R.id.btnDeleteProfile);




        final String username = getIntent().getStringExtra("username");

        u = DBBroker.vratiInstancu(EditProfileActivity.this).getUser(username);

        txtUsernameEdit.setText(u.getUsername());
        txtEmailEdit.setText(u.getEmail());
        txtPasswordEdit.setText(u.getPassword());

        txtUsernameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditProfileActivity.this, "You can't change username!",Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditProfileActivity.this);
                alertDialogBuilder.setTitle("Are you sure you want to delete your profile?");
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Boolean deleted = DBBroker.vratiInstancu(EditProfileActivity.this).deleteUser(u);
                                        if (deleted){
                                            Toast.makeText(EditProfileActivity.this, "Profile successfully deleted!",Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(EditProfileActivity.this, LoginActivity.class);
                                            EditProfileActivity.this.startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(EditProfileActivity.this, "Error. Profile not deleted!",Toast.LENGTH_LONG).show();
                                        }
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
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String a = String.valueOf(txtUsernameEdit.getText());
                String b = String.valueOf(txtEmailEdit.getText());
                if (!validate(b)){
                    Toast.makeText(EditProfileActivity.this,"Email format not correct!",Toast.LENGTH_LONG).show();
                    return;
                }
                String c = String.valueOf(txtPasswordEdit.getText());
                if (c.length()<4){
                    Toast.makeText(EditProfileActivity.this,"Password must be minimum 4 characters long",Toast.LENGTH_LONG).show();
                    return;
                }

                User user = new User(a,b,c,0);

                Boolean updated = DBBroker.vratiInstancu(EditProfileActivity.this).updateUser(user);
                if (updated){
                    Toast.makeText(EditProfileActivity.this, "Profile successfully updated!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(EditProfileActivity.this, "Error. Profile is not updated!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public boolean validate(String emailStr) {
        Matcher matcher = emailRegex.matcher(emailStr);
        return matcher.find();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

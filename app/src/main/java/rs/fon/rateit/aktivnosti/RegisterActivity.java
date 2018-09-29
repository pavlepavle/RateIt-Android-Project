package rs.fon.rateit.aktivnosti;

import android.content.Intent;
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


public class RegisterActivity extends AppCompatActivity {

    StorageDbHelper db;
    public Pattern emailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new StorageDbHelper(this);

        final EditText txtUsernameRegister = (EditText) findViewById(R.id.txtUsernameRegister);
        final EditText txtEmailRegister = (EditText) findViewById(R.id.txtEmailRegister);
        final EditText txtPasswordRegister = (EditText) findViewById(R.id.txtPasswordRegister);

        final Button btnRegister = (Button) findViewById(R.id.btnRegister);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = String.valueOf(txtUsernameRegister.getText());
                if (username.length()<4){
                    Toast.makeText(RegisterActivity.this,"Username must be minimum 4 characters long",Toast.LENGTH_LONG).show();
                    return;
                }
                String email = String.valueOf(txtEmailRegister.getText());
                if (!validate(email)){
                    Toast.makeText(RegisterActivity.this,"Email format not correct!",Toast.LENGTH_LONG).show();
                    return;
                }
                String password = String.valueOf(txtPasswordRegister.getText());
                if (password.length()<4){
                    Toast.makeText(RegisterActivity.this,"Password must be minimum 4 characters long",Toast.LENGTH_LONG).show();
                    return;
                }
                User u1 = new User(username,email,password, 0);
                boolean isInserted = DBBroker.vratiInstancu(RegisterActivity.this).insertUser(u1);
                if (isInserted){
                    Toast.makeText(RegisterActivity.this,"You are registered!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    RegisterActivity.this.startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this,"Username already exists!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean validate(String emailStr) {
        Matcher matcher = emailRegex.matcher(emailStr);
        return matcher.find();
    }

}
package no.ntnu.asd.prosjektfil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The login part is not done yet, is for the improvment part of the code.
 */
public class LoginActivity extends AppCompatActivity {

    private Button signInButton;
    private EditText userName;
    private EditText password;
    private TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signInButton = (Button) findViewById(R.id.signInId);
        userName = (EditText) findViewById(R.id.usernameId);
        password = (EditText) findViewById(R.id.passwordId);
        signUp = (TextView) findViewById(R.id.textViewSignUp);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uName = userName.getText().toString();
                String pWord = password.getText().toString();

              /*  if(!uName.equals("") || !pWord.equals("")) {

                    //kjør innlogging og lagre brukernavn og passord til database


                    if(passord og brukernavn godkjent){
                    Toast.makeText(getApplicationContext(), "Login Was Succesfull!",
                            Toast.LENGTH_LONG).show();

                    startActivity(new Intent(LoginActivity.this, MyProfileActivity.class));

                }else{
                        Toast.makeText(getApplicationContext(),
                                "Wrong Username or Password, please try again",
                                Toast.LENGTH_LONG).show();
                    }
                }*/
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }
}

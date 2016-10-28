package no.ntnu.asd.prosjektfil;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button jobSekrButton;
    Button employerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Employee button
        jobSekrButton = (Button) findViewById(R.id.jobseekerButton);
        jobSekrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JobSeekerActivity.class);
                startActivity(intent);
            }
        });

        // Employer button
        employerBtn = (Button) findViewById(R.id.button2);
        employerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, ListUserActivity.class);
                startActivity(intent);
            }
        });
    }
}

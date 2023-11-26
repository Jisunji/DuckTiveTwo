package com.example.ducktivetwo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button button = findViewById(R.id.buttonStart);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGoalsCompleted();
            }
        });
    }
    public void openGoalsCompleted() {
        Intent intent = new Intent(this, GoalsCompleted.class);
        startActivity(intent);
    }


}
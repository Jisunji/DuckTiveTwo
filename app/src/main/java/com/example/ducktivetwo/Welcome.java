package com.example.ducktivetwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Welcome extends AppCompatActivity {
    Button _btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        _btnStart = (Button) findViewById(R.id.btnStart);
        _btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                openIntro();
            }
        });

    }
    public void openIntro(){
        Intent intent = new Intent(this, IntroDuke.class);
        startActivity(intent);
    }
}
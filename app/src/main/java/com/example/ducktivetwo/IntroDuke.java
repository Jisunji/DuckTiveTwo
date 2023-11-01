package com.example.ducktivetwo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import pl.droidsonroids.gif.GifImageButton;

public class IntroDuke extends AppCompatActivity {

   GifImageButton gifjason;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_duke);

        gifjason = findViewById(R.id.jason);

        gifjason.setOnClickListener(v -> openMenu());


    }
    public void openMenu() {
        Intent menuact = new Intent(this,MainActivity2.class);
        startActivity(menuact);
    }
}
package com.example.ducktivetwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class Splashscreen extends AppCompatActivity {
private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mFirebaseAuth = FirebaseAuth.getInstance();


    }

    @Override
    protected void onStart() {
        super.onStart();

        mFirebaseAuth.getCurrentUser();

        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if(mFirebaseUser!=null){
            startActivity(new Intent(this, MainActivity2.class));

        }
        else{
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

}
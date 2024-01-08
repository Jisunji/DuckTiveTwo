package com.example.ducktivetwo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ducktivetwo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import Model.AchievementsModel;

public class CompanionActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button button1, button2, button3;
    private DatabaseReference reference;
    private FirebaseAuth cAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companion);

        cAuth = FirebaseAuth.getInstance();
        String uId = cAuth.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        reference = database.getReference();

        imageView = findViewById(R.id.imageView);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        //Achievement 1
        AchievementsModel aModel = new AchievementsModel(true, true);
        assert uId != null;
        reference.child("users").child(uId).child("Achievements").child("Achievement1").setValue(aModel);

        //Achievement 2
        AchievementsModel bModel = new AchievementsModel(true,true);
        reference.child("users").child(uId).child("Achievements").child("Achievement2").setValue(bModel);

        //Achievement 3
        AchievementsModel cModel = new AchievementsModel(false,false);
        reference.child("users").child(uId).child("Achievements").child("Achievement3").setValue(cModel);

        checkFileExists("Achievement1",button1);
        checkFileExists("Achievement2",button2);
        checkFileExists("Achievement3",button3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(button1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(button2);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(button3);
            }
        });
    }
    private void checkFileExists(String path, Button button) {
        DatabaseReference AchievementDBR =FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(Objects.requireNonNull(cAuth.getUid()))
                .child("Achievements")
                .child(path);

        AchievementDBR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() &&
                        (dataSnapshot.child("achieved")
                                .getValue() == Boolean.TRUE)) {
                    // The file exists, so enable the button
                    button.setEnabled(true);
                } else {
                    // The file does not exist, so disable the button
                    button.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }

    private void changeImage(Button buttonSelected) {
        if (buttonSelected == button1) {
            imageView.setImageResource(R.drawable.duck1);
        } else if (buttonSelected == button2) {
            imageView.setImageResource(R.drawable.duck2);
        } else if (buttonSelected == button3) {
            imageView.setImageResource(R.drawable.duck3);
        }
    }
}
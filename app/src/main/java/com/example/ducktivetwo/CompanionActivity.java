package com.example.ducktivetwo;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ducktivetwo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    private Button btnAchievement,btnBack;
    private FloatingActionButton fab_plus_btn;
    private FloatingActionButton button1, button2, button3;

    private TextView fab_tasks1_txt;

    private TextView btn2_txt;

    private TextView btn3_txt;
    private boolean isOpen = false;

    private Animation FadeOpen, FadeClose;
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
        button3 = findViewById(R.id.btn3);
        btnAchievement = findViewById(R.id.achievement_btn);
        btnBack = findViewById(R.id.btnBack);


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


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CompanionActivity.this, MainActivity2.class));
            }
        });
        btnAchievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CompanionActivity.this, Achievements.class));
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(button1);
            }
        });

        fab_plus_btn = findViewById(R.id.fabb_plus_btn);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.btn3);


        fab_tasks1_txt = findViewById(R.id.button1_ft_text);
        btn2_txt = findViewById(R.id.button2_ft_text);
        btn3_txt = findViewById(R.id.button3_txt);

        Animation FadeOpen = AnimationUtils.loadAnimation(this, R.anim.fade_open);
        Animation FadeClose = AnimationUtils.loadAnimation(this, R.anim.fade_close);

        fab_plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                design1();
                if (isOpen){
                    button1.startAnimation(FadeClose);
                    button1.setClickable(false);
                    button2.startAnimation(FadeClose);
                    button2.setClickable(false);
                    button3.startAnimation(FadeClose);
                    button3.setClickable(false);

                    fab_tasks1_txt.startAnimation(FadeClose);
                    fab_tasks1_txt.setClickable(false);
                    btn2_txt.startAnimation(FadeClose);
                    btn2_txt.setClickable(false);
                    btn3_txt.startAnimation(FadeClose);
                    btn3_txt.setClickable(false);
                    isOpen=false;
                }
                else {
                    button1.startAnimation(FadeOpen);
                    button1.setClickable(true);
                    button2.startAnimation(FadeOpen);
                    button2.setClickable(true);
                    button3.startAnimation(FadeOpen);
                    button3.setClickable(true);



                    fab_tasks1_txt.startAnimation(FadeOpen);
                    fab_tasks1_txt.setClickable(true);
                    btn2_txt.startAnimation(FadeOpen);
                    btn2_txt.setClickable(true);
                    btn3_txt.startAnimation(FadeOpen);
                    btn3_txt.setClickable(true);

                    isOpen = true;
                }
            }
        });
        return;
    }

    private void design1() {
        //Fab Button task...
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(button2);


            }
        });
    }
    private void design2() {
        //Fab Button task...
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(button3);


            }
        });
    }



    private void checkFileExists(String path, FloatingActionButton button) {
        DatabaseReference AchievementDBR =FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(Objects.requireNonNull(cAuth.getUid()))
                .child("Achievements")
                .child(path);

        AchievementDBR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() &&
                        (dataSnapshot.child("claimed")
                                .getValue() == Boolean.FALSE)) {
                    // Achievement unclaimed
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(CompanionActivity.this,"You have not unlocked that companion!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }

    private void changeImage(FloatingActionButton buttonSelected) {
        if (buttonSelected == button1) {
            imageView.setImageResource(R.drawable.duck1);
        } else if (buttonSelected == button2) {
            imageView.setImageResource(R.drawable.duck2);
        } else if (buttonSelected == button3) {
            imageView.setImageResource(R.drawable.duck3);
        }
    }


}
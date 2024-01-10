package com.example.ducktivetwo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Achievements extends AppCompatActivity {
    private Button btnBack;
    private FirebaseAuth achAuth;
    private DatabaseReference historyDBR;
    private long taskCount;
    private CardView cv1,cv2,cv3,cv4,cv5,cv6;
    private ImageView img1,img2,img3,img4,img5,img6;
    private TextView tv1,tv2,tv3,tv4,tv5,tv6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        cv1 = findViewById(R.id.CardView1);cv2 = findViewById(R.id.CardView2);cv3 = findViewById(R.id.CardView3);
        cv4 = findViewById(R.id.CardView4);cv5 = findViewById(R.id.CardView5);cv6 = findViewById(R.id.CardView6);

        img1 = findViewById(R.id.img1);img2 = findViewById(R.id.img2);img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);img5 = findViewById(R.id.img5);img6 = findViewById(R.id.img6);

        tv1 = findViewById(R.id.tView1);tv2 = findViewById(R.id.tView2);tv3 = findViewById(R.id.tView3);
        tv4 = findViewById(R.id.tView4);tv5 = findViewById(R.id.tView5);tv6 = findViewById(R.id.tView6);



        achAuth = FirebaseAuth.getInstance();
        historyDBR = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(Objects.requireNonNull(achAuth.getCurrentUser()).getUid())
                .child("Tasks_Completed");

        DatabaseReference ref = historyDBR;
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                taskCount = dataSnapshot.getChildrenCount();
                Log.d("MyApp","Files: " + taskCount);
                System.out.println("Number of folders: " + taskCount);
                updateCardViewVisibility();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Achievements.this, CompanionActivity.class));
            }
        });
    }
    private void dimCardView(CardView cardView, boolean dim, ImageView imgView) {
        if (dim) {
            dimDrawable(imgView, 128);
            cardView.setCardBackgroundColor(Color.argb(128, 255, 255, 255)); // Set the background color to semi-transparent white
        } else {
            dimDrawable(imgView, 255);
            cardView.setCardBackgroundColor(Color.WHITE); // Set the background color back to white

        }
    }
    private void updateCardViewVisibility() {
        // Check the criteria here and update the visibility of CardView objects accordingly

        if (taskCount == 1) {
            dimCardView(cv1, false,img1);
        }if (taskCount == 2) {
            dimCardView(cv1, false,img1);
        }if (taskCount >= 3){
            dimCardView(cv3,false,img3);
        }if (taskCount >= 4){
            dimCardView(cv4,false,img4);
        }if (taskCount >= 5){
            dimCardView(cv5,false,img5);
        }if (taskCount >= 6){
            dimCardView(cv6,false,img6);
        }else{
            dimCardView(cv1,true,img1);
            dimCardView(cv2,true,img2);
            dimCardView(cv3,true,img3);
            dimCardView(cv4,true,img4);
            dimCardView(cv5,true,img5);
            dimCardView(cv6,true,img6);
        }
    }
    private void dimDrawable(ImageView imageView, int dimAmount) {
        Drawable drawable = imageView.getDrawable();
        drawable.setAlpha(dimAmount);

        imageView.setImageDrawable(drawable);
    }
}

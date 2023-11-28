package com.example.ducktivetwo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Notifications extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        //notification
        ImageView img9 = findViewById(R.id.imageView21);
        img9.setOnClickListener(v -> openNotifications());


        //toolbar
        ImageView img2 = findViewById(R.id.imageView12);
        ImageView img3 = findViewById(R.id.imageView15);
        ImageView img4 = findViewById(R.id.imageView17);
        ImageView img5 = findViewById(R.id.imageView18);
        ImageView img6 = findViewById(R.id.imageView16);






        img2.setOnClickListener(v -> openDashboard());

        img3.setOnClickListener(v -> openTasks());

        img4.setOnClickListener(v -> openHabits());

        img5.setOnClickListener(v -> openExpense());

        img6.setOnClickListener(v -> openProfile());







    }
    public void openNotifications() {
        Intent intent = new Intent(this, Notifications.class);
        startActivity(intent);
    }

    public void openProfile() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
    public void openDashboard() {
        Intent intent = new Intent(this, GoalsCompleted.class);
        startActivity(intent);
    }

    public void openTasks() {
        Intent intent = new Intent(this, Tasks.class);
        startActivity(intent);
    }

    public void openHabits() {
        Intent intent = new Intent(this, Habits.class);
        startActivity(intent);
    }
    public void openExpense() {
        Intent intent = new Intent(this, Expense2Activity.class);
        startActivity(intent);
    }

    public void openExpense2() {
        Intent intent = new Intent(this, Expense2Activity.class);
        startActivity(intent);
    }






}

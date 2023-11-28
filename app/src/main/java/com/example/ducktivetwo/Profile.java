package com.example.ducktivetwo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //notification
        ImageView img9 = findViewById(R.id.imageView6);
        img9.setOnClickListener(v -> openNotifications());

        TextView tv1 = findViewById(R.id.textView6);
        TextView tv2 = findViewById(R.id.textView7);
        TextView tv3 = findViewById(R.id.textView8);
        TextView tv4 = findViewById(R.id.textView10);

        ImageView img2 = findViewById(R.id.imageView12);
        ImageView img3 = findViewById(R.id.imageView15);
        ImageView img4 = findViewById(R.id.imageView17);
        ImageView img5 = findViewById(R.id.imageView18);
        ImageView img6 = findViewById(R.id.imageView16);



        img2.setOnClickListener(v -> openDashboard());

        img3.setOnClickListener(v -> openTasks());

        img4.setOnClickListener(v -> openHabits());

        img5.setOnClickListener(v -> openExpense());


        boolean currLayout = true;
        if (currLayout){

        }
        else{
            img6.setOnClickListener(v -> openProfile());

        }



        tv1.setOnClickListener(v -> openGoals());

        tv2.setOnClickListener(v -> openChangePass());

        tv3.setOnClickListener(v -> openCompanion());

        tv4.setOnClickListener(v -> openLoguout());
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


    public void openGoals() {
        Intent intent = new Intent(this, Goals.class);
        startActivity(intent);
    }

    public void openChangePass() {
        Intent intent = new Intent(this, ChangePass.class);
        startActivity(intent);
    }

    public void openCompanion() {
        Intent intent = new Intent(this, DucktiveCompanion.class);
        startActivity(intent);
    }

    public void openLoguout() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}

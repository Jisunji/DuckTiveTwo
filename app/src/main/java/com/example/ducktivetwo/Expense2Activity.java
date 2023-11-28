package com.example.ducktivetwo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

// ...

public class Expense2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense2);

        //notification
        ImageView img9 = findViewById(R.id.imageView27);
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

         boolean currLayout = true;
        if (currLayout){

        }
        else{
            img5.setOnClickListener(v -> openExpense());

        }

        img6.setOnClickListener(v -> openProfile());


        BarChart barChart = findViewById(R.id.barChart);

        // Now you can customize your bar chart and add data.
        // Example data:
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, 50f));
        entries.add(new BarEntry(2, 80f));
        entries.add(new BarEntry(3, 60f));
        entries.add(new BarEntry(4, 120f));

        BarDataSet dataSet = new BarDataSet(entries, "Expense Data");
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);




        Animation rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        Animation rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);

        Animation fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);

        Animation toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);

        FloatingActionButton fab1 = findViewById(R.id.floatingActionButton3);
        FloatingActionButton fab2 = findViewById(R.id.floatingActionButton5);




        fab1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (fab2.getVisibility() == View.VISIBLE) {
                    fab2.startAnimation(toBottom);
                    fab2.setVisibility(View.INVISIBLE);
                } else {
                    fab2.startAnimation(fromBottom);
                    fab2.setVisibility(View.VISIBLE);
                }


            }
        });


        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExpense();
            }
        });




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
        Intent intent = new Intent(this, Expense1.class);
        startActivity(intent);
    }

    public void openNotifications() {
        Intent intent = new Intent(this, Notifications.class);
        startActivity(intent);
    }

}


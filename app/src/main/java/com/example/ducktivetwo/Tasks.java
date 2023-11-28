package com.example.ducktivetwo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Tasks extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

       Animation rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
       Animation rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);

       Animation fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);

        Animation toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);

        FloatingActionButton fab1 = findViewById(R.id.floatingActionButton);
        FloatingActionButton fab2 = findViewById(R.id.floatingActionButton4);




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



        //notification
    ImageView img9 = findViewById(R.id.imageView27);
        img9.setOnClickListener(v -> openNotifications());

        //toolbar
        ImageView img2 = findViewById(R.id.imageView12);
        ImageView img3 = findViewById(R.id.imageView15);
        ImageView img4 = findViewById(R.id.imageView17);
        ImageView img5 = findViewById(R.id.imageView18);
        ImageView img6 = findViewById(R.id.imageView16);



        ImageView img10 = findViewById(R.id.imageView24);


        img2.setOnClickListener(v -> openDashboard());


        boolean currLayout = true;
        if (currLayout){

        }
        else{
            img3.setOnClickListener(v -> openTasks());

        }


        img4.setOnClickListener(v -> openHabits());

        img5.setOnClickListener(v -> openExpense());

        img6.setOnClickListener(v -> openProfile());

        img10.setOnClickListener(v -> openTaskInput());

        /*
        eto na yung pang initialize ng values ng spinner at listahan ng tasks
        nasa string.xml yung ibang test values
        ListView listView = findViewById(R.id.editableListView);
        Spinner spinner = findViewById(R.id.firstSpinner);
        ArrayAdapter<CharSequence> listAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.list_items,
                android.R.layout.simple_list_item_1
        );
        listView.setAdapter(listAdapter);

        // Set up ArrayAdapter for Spinner
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_items,
                android.R.layout.simple_spinner_item
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);*/

    }

 

    public void openNotifications() {
        Intent intent = new Intent(this, Notifications.class);
        startActivity(intent);
    }
    public void openTaskInput() {
        Intent intent = new Intent(this, TaskInput.class);
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
}
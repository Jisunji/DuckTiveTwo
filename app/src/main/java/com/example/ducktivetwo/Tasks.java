package com.example.ducktivetwo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Tasks extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        Button button = findViewById(R.id.plusButton);


        //notification
    ImageView img9 = findViewById(R.id.imageView27);
        img9.setOnClickListener(v -> openNotifications());

        //toolbar
        ImageView img2 = findViewById(R.id.imageView12);
        ImageView img3 = findViewById(R.id.imageView15);
        ImageView img4 = findViewById(R.id.imageView17);
        ImageView img5 = findViewById(R.id.imageView18);
        ImageView img6 = findViewById(R.id.imageView16);

        //burger icon
        ImageView img7 = findViewById(R.id.imageView26);

        ImageView img10 = findViewById(R.id.imageView24);

        button.setOnClickListener(v -> openTaskInput());

        img2.setOnClickListener(v -> openDashboard());

        img3.setOnClickListener(v -> openTasks());

        img4.setOnClickListener(v -> openHabits());

        img5.setOnClickListener(v -> openExpense());

        img6.setOnClickListener(v -> openProfile());

        img7.setOnClickListener(v -> openProfile());

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
        Intent intent = new Intent(this, Expense1.class);
        startActivity(intent);
    }
}
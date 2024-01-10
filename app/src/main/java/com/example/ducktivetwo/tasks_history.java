package com.example.ducktivetwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Adapters.TaskHistoryAdapter;
import Model.TaskHistoryData;



public class tasks_history extends AppCompatActivity {

    private DatabaseReference thDatabase;
    private TaskHistoryAdapter thAdapter;
    private RecyclerView taskHistoryRecyclerView;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_history);

        btnBack = findViewById(R.id.btnBack);

        thDatabase = FirebaseDatabase.getInstance().getReference();

        taskHistoryRecyclerView = findViewById(R.id.historyrcv);
        taskHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<TaskHistoryData> options =
                new FirebaseRecyclerOptions.Builder<TaskHistoryData>()
                        .setQuery(thDatabase.child("users")
                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                .child("Tasks_Done"), TaskHistoryData.class)
                        .build();
        thAdapter = new TaskHistoryAdapter(options);
        taskHistoryRecyclerView.setAdapter(thAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(tasks_history.this, MainActivity2.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        thAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        thAdapter.stopListening();
    }
    private void loadDataFromFirebase() {
        DatabaseReference yourDataNodeRef = FirebaseDatabase.getInstance().getReference().child("users").child("Tasks_Done");

        yourDataNodeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<TaskHistoryData> dataList = new ArrayList<>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    TaskHistoryData data = childSnapshot.getValue(TaskHistoryData.class);
                    if (data != null) {
                        dataList.add(data);
                    }
                }
                thAdapter.setTaskHistoryDataList(dataList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}

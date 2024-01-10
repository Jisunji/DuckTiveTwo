package fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ducktivetwo.R;
import com.example.ducktivetwo.tasks_history;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import Adapters.TaskAdapter;
import Model.TaskHistoryData;

public class TasksFragment extends Fragment {
    private FloatingActionButton fabplusfab;
    private FloatingActionButton fabcreatetasks;

    private FloatingActionButton edittask;

    private FloatingActionButton history_btn;

    private DatabaseReference mTaskDatabase,historyDBR;
    private FirebaseAuth tAuth;

    RecyclerView taskRecyclerView;
    TaskAdapter taskAdapter;

    private TextView fab_tasks1_txt;

    private TextView history_txt;

    private TextView edittask_txt;


    //boolean
    private boolean isOpen = false;

    //animation

    private Animation FadeOpen, FadeClose;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tAuth = FirebaseAuth.getInstance();


        View vv = inflater.inflate(R.layout.fragment_tasks, container, false);
        taskRecyclerView = vv.findViewById(R.id.taskrcv);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions<TaskHistoryData> options =
                new FirebaseRecyclerOptions.Builder<TaskHistoryData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users")
                                .child(Objects.requireNonNull(tAuth.getCurrentUser()).getUid()).child("Task_Data"), TaskHistoryData.class)
                        .build();
        taskAdapter = new TaskAdapter(options);
        taskRecyclerView.setAdapter(taskAdapter);
        loadDataFromFirebase();

        FirebaseUser nUser= tAuth.getCurrentUser();
        if(nUser != null) {
            String uid=nUser.getUid();
            mTaskDatabase = FirebaseDatabase.getInstance().getReference()
                    .child("users")
                    .child(uid)
                    .child("Task_Data");

            historyDBR = FirebaseDatabase.getInstance().getReference()
                    .child("users")
                    .child(uid)
                    .child("Tasks_Completed");

            //moveDoneTasks();
            listenForDataChanges();
        }






        // Inflate the layout for this fragment


        fabplusfab = vv.findViewById(R.id.tasks_plus_btn);
        fabcreatetasks = vv.findViewById(R.id.tasks1_ft_btn);
        edittask = vv.findViewById(R.id.taskedit_ft_btn);
        history_btn = vv.findViewById(R.id.taskhistory_ft_btn);

        fab_tasks1_txt = vv.findViewById(R.id.tasks1_ft_text);
        edittask_txt = vv.findViewById(R.id.edittask_ft_text);
        history_txt = vv.findViewById(R.id.taskhistory_ft_text);

        FadeOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_open);
        FadeClose = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_close);

        fabplusfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
                editTasks();
                TaskHistory();

                if (isOpen) {
                    fabcreatetasks.startAnimation(FadeClose);
                    fabcreatetasks.setClickable(false);
                    edittask.startAnimation(FadeClose);
                    edittask.setClickable(false);
                    history_btn.startAnimation(FadeClose);
                    history_btn.setClickable(false);


                    fab_tasks1_txt.startAnimation(FadeClose);
                    fab_tasks1_txt.setClickable(false);
                    edittask_txt.startAnimation(FadeClose);
                    edittask_txt.setClickable(false);
                    history_txt.startAnimation(FadeClose);
                    history_txt.setClickable(false);

                    isOpen = false;
                } else {

                    fabcreatetasks.startAnimation(FadeOpen);
                    fabcreatetasks.setClickable(true);
                    edittask.startAnimation(FadeOpen);
                    edittask.setClickable(true);
                    history_btn.startAnimation(FadeOpen);
                    history_btn.setClickable(true);

                    fab_tasks1_txt.startAnimation(FadeOpen);
                    fab_tasks1_txt.setClickable(true);
                    edittask_txt.startAnimation(FadeOpen);
                    edittask_txt.setClickable(true);
                    history_txt.startAnimation(FadeOpen);
                    history_txt.setClickable(true);


                    isOpen = true;
                }
            }
        });
        return vv;
    }

    private void ftAnimation() {
        if (isOpen) {
            fabcreatetasks.startAnimation(FadeClose);
            fabcreatetasks.setClickable(false);
            edittask.startAnimation(FadeClose);
            edittask.setClickable(false);
            history_btn.startAnimation(FadeClose);
            history_btn.setClickable(false);

            fab_tasks1_txt.startAnimation(FadeClose);
            fab_tasks1_txt.setClickable(false);
            edittask_txt.startAnimation(FadeClose);
            edittask_txt.setClickable(false);
            history_txt.startAnimation(FadeClose);
            history_txt.setClickable(false);

            isOpen = false;
        } else {
            fabcreatetasks.startAnimation(FadeOpen);
            fabcreatetasks.setClickable(true);

            edittask.startAnimation(FadeOpen);
            edittask.setClickable(true);

            history_btn.startAnimation(FadeOpen);
            history_btn.setClickable(true);

            fab_tasks1_txt.startAnimation(FadeOpen);
            fab_tasks1_txt.setClickable(true);

            edittask_txt.startAnimation(FadeOpen);
            edittask_txt.setClickable(true);

            history_txt.startAnimation(FadeOpen);
            history_txt.setClickable(true);

            isOpen = true;
        }
    }

    private void addData() {
        //Fab Button task...
        fabcreatetasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            taskDataInsert();

            }
        });
    }

    private void editTasks() {
        edittask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TasksEditData();
            }
        });
    }

    private void TaskHistory() {
        history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskHistory();
            }
        });
    }

    public void TasksEditData() {
        AlertDialog.Builder mydialogg = new AlertDialog.Builder(getActivity());
        LayoutInflater inflaterr = LayoutInflater.from(getActivity());
        View view = inflaterr.inflate(R.layout.custom_layout_for_edittask,null);
        mydialogg.setView(view);
        final AlertDialog dialogg = mydialogg.create();

        dialogg.setCancelable(false);

        EditText edtTaskName = view.findViewById(R.id.taskname_edt);
        EditText edtTaskStat = view.findViewById(R.id.statustask_edt);

        Button btnSave = view.findViewById(R.id.btnSave);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = edtTaskName.getText().toString().trim();
                String newTaskStat = edtTaskStat.getText().toString().trim();
                boolean b = true;

                Pattern pattern = Pattern.compile("^(Done|OnGoing|ToDo|Missed)$");

                if(TextUtils.isEmpty(taskName)){
                    edtTaskName.setError("Required Field..");
                    return;
                }
                if (!pattern.matcher(newTaskStat).matches()) {
                    edtTaskStat.setError(("Invalid Input..."));
                    return;
                }

                if(TextUtils.isEmpty(newTaskStat)){
                    edtTaskStat.setError("Required Field..");
                    return;
                }
                DatabaseReference TaskDBR =FirebaseDatabase.getInstance().getReference()
                        .child("users")
                        .child(Objects.requireNonNull(tAuth.getUid()))
                        .child("Task_Data")
                        .child(taskName);

                TaskDBR.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // The file exists
                            TaskDBR.child("status").setValue(newTaskStat);
                            Toast.makeText(getActivity(), "TASK STATUS EDITED", Toast.LENGTH_SHORT).show();
                            ftAnimation();
                            dialogg.dismiss();

                        } else {
                            // The file does not exist
                            edtTaskName.setError("Task does not exist");
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle errors
                        Log.println(Log.ERROR,"Database","Error");
                    }
                });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftAnimation();
                dialogg.dismiss();
            }
        });
        dialogg.show();
    }

    public void taskDataInsert() {
        AlertDialog.Builder mydialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        View myview = inflater.inflate(R.layout.custom_layout_for_creatingtasks, null);

        mydialog.setView(myview);

        final AlertDialog dialog = mydialog.create();

        dialog.setCancelable(false);

        EditText edtTaskName = myview.findViewById(R.id.taskname_edt);
        EditText edtCategory = myview.findViewById(R.id.category_edt);
        EditText edtDescription = myview.findViewById(R.id.description_edt);
        EditText edtPriority = myview.findViewById(R.id.priority_edt);
        EditText edtEditDate = myview.findViewById(R.id.editTextDate);
        EditText edtTime = myview.findViewById(R.id.time_edt);


        Button btnSave = myview.findViewById(R.id.btnSave);
        Button btnCancel = myview.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String priority = edtPriority.getText().toString().trim();
                String category = edtCategory.getText().toString().trim();
                String taskName = edtTaskName.getText().toString().trim();
                String description = edtDescription.getText().toString().trim();
                String edit = edtEditDate.getText().toString().trim();
                String time = edtTime.getText().toString().trim();


                if (TextUtils.isEmpty(priority)) {
                    edtPriority.setError(("Require input High, Medium, Low Priority.."));
                    return;
                }

                // Create a regex pattern
                Pattern pattern = Pattern.compile("^(Low|Medium|High)$");

                Pattern patternn = Pattern.compile("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$");


                if (!patternn.matcher(time).matches()) {
                    edtTime.setError(("Required Field.."));
                    return;
                }

                if (TextUtils.isEmpty(edit)) {
                    edtEditDate.setError(("Require input.."));
                    return;
                }

                if (!pattern.matcher(priority).matches()) {
                    edtPriority.setError(("Input High, Medium, Low Priority"));
                    return;
                }

                if (TextUtils.isEmpty(category)) {
                    edtCategory.setError("Required Field..");
                    return;
                }

                if (TextUtils.isEmpty(taskName)) {
                    edtTaskName.setError("Required Field..");
                    return;
                }

                if (TextUtils.isEmpty(description)) {
                    edtDescription.setError("Required Field..");
                    return;
                }
                String id = mTaskDatabase.push().getKey();

                String status = "To Do";

                TaskHistoryData data1 = new TaskHistoryData(taskName, category, description, priority, edit, status, time);

                mTaskDatabase.child(taskName).setValue(data1);
                Toast.makeText(getActivity(), "TASK DATA ADDED", Toast.LENGTH_SHORT).show();
                ftAnimation();
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftAnimation();
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void taskHistory(){
        history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), tasks_history.class));


            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        taskAdapter.startListening();
    }
    @Override
    public void onStop(){
        super.onStop();
        taskAdapter.stopListening();
    }
    private void loadDataFromFirebase() {
        DatabaseReference yourDataNodeRef = FirebaseDatabase.getInstance().getReference().child("users").child("Task_Data");

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
                taskAdapter.setTaskDataList(dataList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void checkGrandchild(DataSnapshot snapshot) {
        DatabaseReference newDestination = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(tAuth.getCurrentUser().getUid())
                .child("Tasks_Done");

        for (DataSnapshot child : snapshot.getChildren()) {
            if (child.child("status").getValue() != null && child.child("status").getValue().equals("Done")) {
                // The condition is met, perform the desired action here.
                System.out.println("The grandchild node contains an object with the key 'status' and a string value of 'done'.");

                newDestination.child(Objects.requireNonNull(child.getKey())).setValue(child.getValue());
                child.getRef().removeValue();
            }
        }
    }
    private void listenForDataChanges() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users")
                .child(tAuth.getCurrentUser().getUid())
                .child("Task_Data");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                checkGrandchild(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value." + error.toException());
            }
        });
    }
}
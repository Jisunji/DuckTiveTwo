package fragments;

import android.app.AlertDialog;
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
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import Adapters.TaskAdapter;
import Model.TaskData;

public class TasksFragment extends Fragment {
    private FloatingActionButton fabplusfab;
    private FloatingActionButton fabcreatetasks;
    private DatabaseReference mTaskDatabase;
    private FirebaseAuth tAuth;

    RecyclerView taskRecyclerView;
    TaskAdapter taskAdapter;

    private TextView fab_tasks1_txt;


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

        FirebaseRecyclerOptions<TaskData> options =
                new FirebaseRecyclerOptions.Builder<TaskData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users")
                                .child(Objects.requireNonNull(tAuth.getCurrentUser()).getUid()).child("Task_Data"), TaskData.class)
                        .build();
        taskAdapter = new TaskAdapter(options);
        taskRecyclerView.setAdapter(taskAdapter);
        loadDataFromFirebase();

        FirebaseUser nUser= tAuth.getCurrentUser();
        if(nUser != null) {
            String uid=nUser.getUid();
            mTaskDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Task_Data");
        }



        // Inflate the layout for this fragment


        fabplusfab = vv.findViewById(R.id.tasks_plus_btn);
        fabcreatetasks = vv.findViewById(R.id.tasks1_ft_btn);

        fab_tasks1_txt = vv.findViewById(R.id.tasks1_ft_text);

        FadeOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_open);
        FadeClose = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_close);

        fabplusfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
                if (isOpen) {
                    fabcreatetasks.startAnimation(FadeClose);
                    fabcreatetasks.setClickable(false);

                    fab_tasks1_txt.startAnimation(FadeClose);
                    fab_tasks1_txt.setClickable(false);
                    isOpen = false;
                } else {
                    fabcreatetasks.startAnimation(FadeOpen);
                    fabcreatetasks.setClickable(true);

                    fab_tasks1_txt.startAnimation(FadeOpen);
                    fab_tasks1_txt.setClickable(true);

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


            fab_tasks1_txt.startAnimation(FadeClose);

            fab_tasks1_txt.setClickable(false);

            isOpen = false;
        } else {
            fabcreatetasks.startAnimation(FadeOpen);

            fabcreatetasks.setClickable(true);


            fab_tasks1_txt.startAnimation(FadeOpen);

            fab_tasks1_txt.setClickable(true);

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

    public void TasksEditData() {
        AlertDialog.Builder mydialogg = new AlertDialog.Builder(getActivity());
        LayoutInflater inflaterr = LayoutInflater.from(getActivity());
        View view = inflaterr.inflate(R.layout.custom_layout_for_edithabits,null);
        mydialogg.setView(view);
        final AlertDialog dialogg = mydialogg.create();

        dialogg.setCancelable(false);

        EditText edthabittaskname = view.findViewById(R.id.habitname_edt);
        EditText edtedit = view.findViewById(R.id.statusedit_edt);

        Button btnSave = view.findViewById(R.id.btnSave);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String habittaskname = edthabittaskname.getText().toString().trim();
                String edittask = edtedit.getText().toString().trim();
                boolean b = true;

                Pattern pattern = Pattern.compile("^(Done|OnGoing|ToDo)$");

                if(TextUtils.isEmpty(habittaskname)){
                    edthabittaskname.setError("Required Field..");
                    return;
                }
                if (!pattern.matcher(edittask).matches()) {
                    edtedit.setError(("Invalid Input..."));
                    return;
                }

                if(TextUtils.isEmpty(edittask)){
                    edtedit.setError("Required Field..");
                    return;
                }
                DatabaseReference HabitDBR =FirebaseDatabase.getInstance().getReference()
                        .child("users")
                        .child(Objects.requireNonNull(tAuth.getUid()))
                        .child("Task_Data")
                        .child(habittaskname);

                HabitDBR.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // The file exists
                            HabitDBR.child("habitStatus").setValue(edittask);
                            Toast.makeText(getActivity(), "HABIT STATUS EDITED", Toast.LENGTH_SHORT).show();
                            ftAnimation();
                            dialogg.dismiss();

                        } else {
                            // The file does not exist
                            edthabittaskname.setError("Habit does not exist");
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

                //String mDate = DateFormat.getDateInstance().format(new Date());

                String status = "To Do";

                TaskData data1 = new TaskData(taskName, category, description, priority, edit, status, time);

                mTaskDatabase.child(id).setValue(data1);
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
                List<TaskData> dataList = new ArrayList<>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    TaskData data = childSnapshot.getValue(TaskData.class);
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
}
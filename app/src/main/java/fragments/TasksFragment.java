package fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
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

        Button btnSave = myview.findViewById(R.id.btnSave);
        Button btnCancel = myview.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String priority = edtPriority.getText().toString().trim();
                String category = edtCategory.getText().toString().trim();
                String taskName = edtTaskName.getText().toString().trim();
                String description = edtDescription.getText().toString().trim();

                if (TextUtils.isEmpty(priority)) {
                    edtPriority.setError(("Require input High, Medium, Low Priority.."));
                    return;
                }

                // Create a regex pattern
                Pattern pattern = Pattern.compile("^(Low|Medium|High)$");

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

                String mDate = DateFormat.getDateInstance().format(new Date());

                TaskData data1 = new TaskData(taskName, category, description, priority, mDate);

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
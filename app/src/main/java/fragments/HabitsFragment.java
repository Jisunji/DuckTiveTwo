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
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import Adapters.HabitAdapter;
import Adapters.TaskAdapter;
import Model.HabitData;
import Model.TaskData;


public class HabitsFragment extends Fragment {

    private FloatingActionButton habitplusfab;
    private FloatingActionButton habitsbtn;

    private TextView fab_habits_txt;

    private boolean isOpen = false;

    private Animation FadeOpen, FadeClose;


    //Firebase...
    private DatabaseReference mHabitDatabase;
    private FirebaseAuth hAuth;


    RecyclerView habitRecyclerView;
    HabitAdapter habitAdapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        hAuth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        View vvv = inflater.inflate(R.layout.fragment_habits, container, false);
        habitRecyclerView = vvv.findViewById(R.id.habitRCV);
        habitRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions<HabitData> options =
                new FirebaseRecyclerOptions.Builder<HabitData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users")
                                .child(Objects.requireNonNull(hAuth.getCurrentUser()).getUid()).child("Habit_Data"), HabitData.class)
                        .build();
        habitAdapter = new HabitAdapter(options);
        habitRecyclerView.setAdapter(habitAdapter);
        loadDataFromFirebase();

        FirebaseUser nUser= hAuth.getCurrentUser();
        if(nUser != null) {
            String uid=nUser.getUid();
            mHabitDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Habit_Data");
        }

        // Habits FAB
        habitplusfab = vvv.findViewById(R.id.habits_plus_btn);
        habitsbtn = vvv.findViewById(R.id.habits_ft_btn);

        fab_habits_txt = vvv.findViewById(R.id.habits_ft_text);

        FadeOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_open);
        FadeClose = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_close);

        habitplusfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHabits();

                if (isOpen) {
                    habitsbtn.startAnimation(FadeClose);
                    habitsbtn.setClickable(false);

                    fab_habits_txt.startAnimation(FadeClose);
                    fab_habits_txt.setClickable(false);
                    isOpen = false;
                } else {
                    habitsbtn.startAnimation(FadeOpen);
                    habitsbtn.setClickable(true);

                    fab_habits_txt.startAnimation(FadeOpen);
                    fab_habits_txt.setClickable(true);

                    isOpen = true;
                }
            }
        });
        return vvv;
    }

    private void addHabits() {
        habitsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habitsDataInsert();
            }
        });
    }

    private void ftAnimation() {
        if (isOpen) {
            habitsbtn.startAnimation(FadeClose);

            habitsbtn.setClickable(false);


            fab_habits_txt.startAnimation(FadeClose);

            fab_habits_txt.setClickable(false);

            isOpen = false;
        } else {
            habitsbtn.startAnimation(FadeOpen);

            habitsbtn.setClickable(true);


            fab_habits_txt.startAnimation(FadeOpen);

            fab_habits_txt.setClickable(true);

            isOpen = true;
        }
    }

    public void habitsDataInsert() {
        AlertDialog.Builder mydialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View myview = inflater.inflate(R.layout.custom_layout_for_creatinghabits, null);
        mydialog.setView(myview);
        final AlertDialog dialog = mydialog.create();

        dialog.setCancelable(false);

        EditText edthabitsname = myview.findViewById(R.id.habitsname_edt);
        EditText edthabitsDescription = myview.findViewById(R.id.habitsdescription_edt);
        EditText edthabitsTime = myview.findViewById(R.id.habitstime_edt);

        Button btnSave = myview.findViewById(R.id.btnSave);
        Button btnCancel = myview.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String habitName = edthabitsname.getText().toString().trim();
                String habitDescription = edthabitsDescription.getText().toString().trim();
                String habitTime = edthabitsTime.getText().toString().trim();

                if (TextUtils.isEmpty(habitName)) {
                    edthabitsname.setError("Required Field..");
                    return;
                }
                if (TextUtils.isEmpty(habitDescription)) {
                    edthabitsDescription.setError("Required Field..");
                    return;
                }
                if (TextUtils.isEmpty(habitTime)) {
                    edthabitsTime.setError("Required Field..");
                    return;
                }
                String id = mHabitDatabase.push().getKey();

                String mDate = DateFormat.getDateInstance().format(new Date());

                HabitData data1 = new HabitData(habitName, habitDescription, habitTime, mDate);

                mHabitDatabase.child(id).setValue(data1);
                Toast.makeText(getActivity(), "HABIT DATA ADDED", Toast.LENGTH_SHORT).show();
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
        habitAdapter.startListening();
    }
    @Override
    public void onStop(){
        super.onStop();
        habitAdapter.stopListening();
    }
    private void loadDataFromFirebase() {
        DatabaseReference yourDataNodeRef = FirebaseDatabase.getInstance().getReference().child("users").child("Habit_Data");

        yourDataNodeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<HabitData> dataList = new ArrayList<>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    HabitData data = childSnapshot.getValue(HabitData.class);
                    if (data != null) {
                        dataList.add(data);
                    }
                }
                habitAdapter.setHabitDataList(dataList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}
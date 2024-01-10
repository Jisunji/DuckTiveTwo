package fragments;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import Adapters.HabitAdapter;

import com.example.ducktivetwo.HabitsAlarmReceiver;
import Model.HabitData;



public class HabitsFragment extends Fragment {

    private FloatingActionButton habitplusfab;

    private FloatingActionButton habitedtfab;


    private FloatingActionButton habitsbtn;


    private TextView fab_edit_txt;
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
        habitedtfab = vvv.findViewById(R.id.habitedit_ft_btn);


        fab_habits_txt = vvv.findViewById(R.id.habits_ft_text);
        fab_edit_txt = vvv.findViewById(R.id.edithab_ft_text);


        FadeOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_open);
        FadeClose = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_close);

        habitplusfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHabits();
                editHabits();


                if (isOpen) {
                    habitsbtn.startAnimation(FadeClose);
                    habitsbtn.setClickable(false);
                    habitedtfab.startAnimation(FadeClose);
                    habitedtfab.setClickable(false);

                    fab_habits_txt.startAnimation(FadeClose);
                    fab_habits_txt.setClickable(false);
                    fab_edit_txt.startAnimation(FadeClose);
                    fab_edit_txt.setClickable(false);

                    isOpen = false;
                } else {
                    habitsbtn.startAnimation(FadeOpen);
                    habitsbtn.setClickable(true);
                    habitedtfab.startAnimation(FadeOpen);
                    habitedtfab.setClickable(true);

                    fab_habits_txt.startAnimation(FadeOpen);
                    fab_habits_txt.setClickable(true);
                    fab_edit_txt.startAnimation(FadeOpen);
                    fab_edit_txt.setClickable(true);

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

   private void editHabits() {
       habitedtfab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               habitsEditData();
           }
       });
   }


    private void ftAnimation() {
        if (isOpen) {
            habitsbtn.startAnimation(FadeClose);

            habitsbtn.setClickable(false);

            habitedtfab.startAnimation(FadeClose);
            habitedtfab.setClickable(false);


            fab_habits_txt.startAnimation(FadeClose);


            fab_habits_txt.setClickable(false);

            fab_edit_txt.startAnimation(FadeClose);
            fab_edit_txt.setClickable(false);

            isOpen = false;
        } else {
            habitsbtn.startAnimation(FadeOpen);

            habitsbtn.setClickable(true);

            habitedtfab.startAnimation(FadeOpen);
            habitedtfab.setClickable(true);

            fab_habits_txt.startAnimation(FadeOpen);

            fab_habits_txt.setClickable(true);

            fab_edit_txt.startAnimation(FadeOpen);
            fab_edit_txt.setClickable(true);

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
                String habitStatus = "To Do";

                String input = "24:00"; // change this line to test with different inputs
                Pattern pattern = Pattern.compile("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$");


                if (!pattern.matcher(habitTime).matches()) {
                    edthabitsTime.setError(("Required Field.."));
                    return;
                }

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

                HabitData data1 = new HabitData(habitName, habitDescription, habitTime, mDate, habitStatus );

                assert id != null;
                mHabitDatabase.child(habitName).setValue(data1);
                Toast.makeText(getActivity(), "HABIT DATA ADDED", Toast.LENGTH_SHORT).show();

                ftAnimation();
                dialog.dismiss();

                scheduleAlarms();

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



    public void scheduleAlarms() {

        String userUid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        DatabaseReference habitDataRef = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(userUid)
                .child("Habit_Data");


        habitDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                int requestCodeCounter = 0;

                for (DataSnapshot habitSnapshot : dataSnapshot.getChildren()) {

                    HabitData habitData = habitSnapshot.getValue(HabitData.class);

Log.d("Habit Time ", String.valueOf(habitData.getHabitTime()));
                    if (habitData != null && habitData.getHabitTime() != null && !habitData.getHabitTime().isEmpty()) {

                        String habitTime = habitData.getHabitTime();
                        String[] timeComponents = habitTime.split(":");

                        if (timeComponents.length == 2) {
                            int hours = Integer.parseInt(timeComponents[0]);
                            int minutes = Integer.parseInt(timeComponents[1]);


                            Calendar habitTimeCalendar = Calendar.getInstance();
                            habitTimeCalendar.set(Calendar.HOUR_OF_DAY, hours);
                            habitTimeCalendar.set(Calendar.MINUTE, minutes);
                            habitTimeCalendar.set(Calendar.SECOND, 0);

                            long habitTimeMillis = habitTimeCalendar.getTimeInMillis();

                            int requestCodeExact = requestCodeCounter++;
                            long currentTimeMillis = System.currentTimeMillis();

                            if (habitTimeMillis <= currentTimeMillis) {
                                // The calculated time is in the past. Adjust the time or handle accordingly.
                                Log.d("MyApp", "Scheduled time is in the past.");
                            } else {
                                // Proceed with scheduling the notification.
                                scheduleNotification(getContext(), habitTimeMillis, "Habit Title - "+ habitData.getHabitName(), requestCodeExact);
                            }



                        }
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors, if any
            }
        });
    }

    private void scheduleNotification(Context context, long timeInMillis, String title, int requestCode) {



        Intent notificationIntentExact = new Intent(context, HabitsAlarmReceiver.class);
        notificationIntentExact.setAction("ACTION_EXACT");
        notificationIntentExact.putExtra("title", title);
        notificationIntentExact.putExtra("message", " Time is up!");

        // Use different request codes for each PendingIntent
        PendingIntent pendingIntentExact = PendingIntent.getBroadcast(context, requestCode + 1, notificationIntentExact, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        if (alarmManager != null) {
            // Schedule alarm at the exact habitTime
            alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntentExact);
        }


    }





    public void habitsEditData() {
        AlertDialog.Builder mydialogg = new AlertDialog.Builder(getActivity());
        LayoutInflater inflaterr = LayoutInflater.from(getActivity());
        View view = inflaterr.inflate(R.layout.custom_layout_for_edithabits,null);
        mydialogg.setView(view);
        final AlertDialog dialogg = mydialogg.create();

        dialogg.setCancelable(false);

        EditText edthabitname = view.findViewById(R.id.habitname_edt);
        EditText edthabitstat = view.findViewById(R.id.statusedit_edt);

        Button btnSave = view.findViewById(R.id.btnSave);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String habitname = edthabitname.getText().toString().trim();
                String newHabitStat = edthabitstat.getText().toString().trim();
                boolean b = true;

                Pattern pattern = Pattern.compile("^(Done|OnGoing|ToDo)$");

                if(TextUtils.isEmpty(habitname)){
                    edthabitname.setError("Required Field..");
                    return;
                }
                if (!pattern.matcher(newHabitStat).matches()) {
                    edthabitstat.setError(("Invalid Input..."));
                    return;
                }

                if(TextUtils.isEmpty(newHabitStat)){
                    edthabitstat.setError("Required Field..");
                    return;
                }
                DatabaseReference HabitDBR =FirebaseDatabase.getInstance().getReference()
                        .child("users")
                        .child(Objects.requireNonNull(hAuth.getUid()))
                        .child("Habit_Data")
                        .child(habitname);

                HabitDBR.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // The file exists
                            HabitDBR.child("habitStatus").setValue(newHabitStat);
                            Toast.makeText(getActivity(), "HABIT STATUS EDITED", Toast.LENGTH_SHORT).show();
                            ftAnimation();
                             dialogg.dismiss();

                        } else {
                            // The file does not exist
                            edthabitname.setError("Habit does not exist");
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

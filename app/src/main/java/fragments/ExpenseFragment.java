package fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
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

import Adapters.MyAdapter;
import Model.Data;

public class ExpenseFragment extends Fragment {

    //Floating Button

    private FloatingActionButton fab_main_btn;
    private FloatingActionButton fab_threshold_btn;
    private FloatingActionButton fab_expense_btn;

    //Floating Button

    private TextView fab_threshold_txt;
    private TextView fab_expense_txt;

    //boolean
    private boolean isOpen=false;

    //animation

    private Animation FadeOpen,FadeClose;

    //Firebase...
    private DatabaseReference mExpenseDatabase;
    private DatabaseReference mThresholdDatabase;
    private FirebaseAuth mAuth;

    //Dashboard expense result..

    private TextView totalExpenseResult;

    RecyclerView recyclerView;
    MyAdapter myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();

        View myview = inflater.inflate(R.layout.fragment_expense, container, false);
        TextView threshold = myview.findViewById(R.id.threshold_text_result);
        recyclerView = (RecyclerView)myview.findViewById(R.id.rcv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions<Data> options =
                new FirebaseRecyclerOptions.Builder<Data>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users")
                                .child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child("Expense_Data"), Data.class)
                        .build();
        myAdapter = new MyAdapter(options);
        recyclerView.setAdapter(myAdapter);
        loadDataFromFirebase();
        loadThresholdFromFirebase(threshold);

        FirebaseUser nUser= mAuth.getCurrentUser();
        if(nUser != null) {
            String uid=nUser.getUid();
            mExpenseDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Expense_Data");
            mThresholdDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Expense_Threshold");
        }




        //Connect floating action button to layout
        fab_main_btn=myview.findViewById(R.id.fb_main_plus_btn);
        fab_threshold_btn=myview.findViewById(R.id.threshold_ft_btn);
        //fab_income_btn=myview.findViewById(R.id.income_ft_btn);
        fab_expense_btn=myview.findViewById(R.id.expense_ft_btn);

        //Connect Floating Text
        //fab_income_txt=myview.findViewById(R.id.income_ft_text);
        fab_threshold_txt=myview.findViewById(R.id.threshold_ft_text);
        fab_expense_txt=myview.findViewById(R.id.expense_ft_text);

        //Total expense result..

        totalExpenseResult=myview.findViewById(R.id.expense_text_result);

        //Animation Connect..

        FadeOpen= AnimationUtils.loadAnimation(getActivity(),R.anim.fade_open);
        FadeClose=AnimationUtils.loadAnimation(getActivity(),R.anim.fade_close);

        fab_main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addData();
                if (isOpen){
                    fab_threshold_btn.startAnimation(FadeClose);
                    fab_expense_btn.startAnimation(FadeClose);
                    fab_threshold_btn.setClickable(false);
                    fab_expense_btn.setClickable(false);

                    fab_threshold_txt.startAnimation(FadeClose);
                    fab_expense_txt.startAnimation(FadeClose);
                    fab_threshold_txt.setClickable(false);
                    fab_expense_txt.setClickable(false);
                    isOpen=false;
                }else{
                    fab_threshold_btn.startAnimation(FadeOpen);
                    fab_expense_btn.startAnimation(FadeOpen);
                    fab_threshold_btn.setClickable(true);
                    fab_expense_btn.setClickable(true);

                    fab_threshold_txt.startAnimation(FadeOpen);
                    fab_expense_txt.startAnimation(FadeOpen);
                    fab_threshold_txt.setClickable(true);
                    fab_expense_txt.setClickable(true);

                    isOpen=true;
                }
            }
        });

        //total expense

        mExpenseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalsum = 0;
                for (DataSnapshot mysnapshot:snapshot.getChildren()){

                    Data data=mysnapshot.getValue(Data.class);
                    totalsum+=data.getAmount();

                    String strTotalSum=String.valueOf(totalsum);

                    totalExpenseResult.setText(strTotalSum);
                    if(Long.parseLong(strTotalSum) >= (Long.parseLong(threshold.getText().toString()))/2){
                        Toast.makeText(getActivity(), "Nearing expense threshold", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return myview;
    }



    private void ftAnimation(){
        if (isOpen){
            fab_threshold_btn.startAnimation(FadeClose);
            fab_expense_btn.startAnimation(FadeClose);
            fab_threshold_btn.setClickable(false);
            fab_expense_btn.setClickable(false);

            fab_threshold_txt.startAnimation(FadeClose);
            fab_expense_txt.startAnimation(FadeClose);
            fab_threshold_txt.setClickable(false);
            fab_expense_txt.setClickable(false);
            isOpen=false;
        }else{
            fab_threshold_btn.startAnimation(FadeOpen);
            fab_expense_btn.startAnimation(FadeOpen);
            fab_threshold_btn.setClickable(true);
            fab_expense_btn.setClickable(true);

            fab_threshold_txt.startAnimation(FadeOpen);
            fab_expense_txt.startAnimation(FadeOpen);
            fab_threshold_txt.setClickable(true);
            fab_expense_txt.setClickable(true);
            isOpen=true;
        }
    }
    private void addData(){
        fab_expense_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expenseDataInsert();
            }
        });

        fab_threshold_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thresholdDataInsert();
            }
        });
    }

    public void expenseDataInsert(){
        AlertDialog.Builder myDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        View myView = inflater.inflate(R.layout.custom_layout_for_insertdata, null);

        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();

        dialog.setCancelable(false);

        EditText amount = myView.findViewById(R.id.amount_edt);
        EditText type = myView.findViewById(R.id.type_edt);
        EditText note = myView.findViewById(R.id.note_edt);

        Button btnSave = myView.findViewById(R.id.btnSave);
        Button btnCancel = myView.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmAmount = amount.getText().toString().trim();
                String tmType = type.getText().toString().trim();
                String tmNote = note.getText().toString().trim();

                if(TextUtils.isEmpty(tmAmount)){
                    amount.setError("Required field...");
                    return;
                }

                int inamount = Integer.parseInt(tmAmount);
                if(TextUtils.isEmpty(tmType)){
                    type.setError("Required field...");
                    return;
                }
                if(TextUtils.isEmpty(tmNote)){
                    note.setError("Required field...");
                    return;
                }

                String id = mExpenseDatabase.push().getKey();

                String mDate = DateFormat.getDateInstance().format(new Date());

                Data data1 = new Data(inamount, tmType, tmNote, id, mDate);

                mExpenseDatabase.child(id).setValue(data1);
                Toast.makeText(getActivity(), "EXPENSE DATA ADDED", Toast.LENGTH_SHORT).show();
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

    public void thresholdDataInsert(){
        AlertDialog.Builder myDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        View myvieww = inflater.inflate(R.layout.custom_layout_for_threshold,null);

        myDialog.setView(myvieww);

        final AlertDialog dialog = myDialog.create();

        dialog.setCancelable(false);

        EditText amount =myvieww.findViewById(R.id.thresamount_edt);

        Button btnSave =myvieww.findViewById(R.id.btnSave);
        Button btnCancel =myvieww.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Thamount =amount.getText().toString().trim();

                if(TextUtils.isEmpty(Thamount)){
                    amount.setError("Required Amount...");
                    return;
                }

                int inamount = Integer.parseInt(Thamount);
                mThresholdDatabase.setValue(inamount);
                Toast.makeText(getActivity(), "THRESHOLD DATA ADDED", Toast.LENGTH_SHORT).show();
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
        myAdapter.startListening();
    }
    @Override
    public void onStop(){
        super.onStop();
        myAdapter.stopListening();
    }

    private void loadThresholdFromFirebase(TextView textView){
        DatabaseReference yourDataNodeRef = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid()).child("Expense_Threshold");
        yourDataNodeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = String.valueOf(dataSnapshot.getValue(Long.class));
                if(value.equals("null")){
                    textView.setText("000.00");
                }
                else{
                    textView.setText((value));
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value

            }
        });

    }


   private void loadDataFromFirebase() {
        DatabaseReference yourDataNodeRef = FirebaseDatabase.getInstance().getReference().child("users").child("Expense_Data");

        yourDataNodeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Data> dataList = new ArrayList<>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Data data = childSnapshot.getValue(Data.class);
                    if (data != null) {
                        dataList.add(data);
                    }
                }
                myAdapter.setExpenseDataList(dataList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });


    }

}
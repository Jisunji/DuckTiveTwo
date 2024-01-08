package fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ducktivetwo.CompanionActivity;
import com.example.ducktivetwo.MainActivity;
import com.example.ducktivetwo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Profile extends Fragment {
    private TextView userName, eMail;
    private Button logOut, companion;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mUserDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser nUser= mFirebaseAuth.getCurrentUser();
        if(nUser != null) {
            String uid = nUser.getUid();
            mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("user_data");
        }


        View yview = inflater.inflate(R.layout.fragment_profile, container, false);

        userName = yview.findViewById(R.id.user_Name);
        eMail = yview.findViewById(R.id.user_eMail);
        logOut = yview.findViewById(R.id.btnLogout);
        companion = yview.findViewById(R.id.btnCompanion);


        companion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CompanionActivity.class));
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View b) {
                mFirebaseAuth.signOut();
                startActivity(new Intent(getActivity(),MainActivity.class));
            }
        });

        return yview;
    }

    @Override
    public void onStart() {
        super.onStart();


        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser != null) {
            eMail.setText(mFirebaseUser.getEmail());
            ValueEventListener users = mUserDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String yourItem = dataSnapshot.child("username").getValue(String.class);
                    userName.setText(yourItem);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
}
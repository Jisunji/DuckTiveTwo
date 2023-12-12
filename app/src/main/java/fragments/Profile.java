package fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ducktivetwo.MainActivity;
import com.example.ducktivetwo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Profile extends Fragment {
    private TextView userName, eMail;
    private Button logOut;
    private FirebaseAuth mFirebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFirebaseAuth = FirebaseAuth.getInstance();

        View yview = inflater.inflate(R.layout.fragment_profile, container, false);

        userName = yview.findViewById(R.id.user_Name);
        eMail = yview.findViewById(R.id.user_eMail);
        logOut = yview.findViewById(R.id.btnLogout);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View a) {
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
            userName.setText(mFirebaseUser.getDisplayName());
        }
    }

}
package com.example.ducktivetwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Objects;

public class LogIn extends AppCompatActivity {

    private Button btnLogin2;
    private EditText loginUsername, loginPassword;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        TextView forgetPass;
        forgetPass = findViewById(R.id.txtClickable2);
        loginUsername = findViewById(R.id.login_uname);
        loginPassword = findViewById(R.id.login_password);
        btnLogin2 = (Button) findViewById(R.id.btnLogin2);
        String text = "Forget Password?";

        btnLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateUsername() || !validatePassword()){

                } else{
                    checkUser();
                }
            }
        });


        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Toast.makeText(LogIn.this, "One", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void updateDrawState(TextPaint ds1){
                super.updateDrawState(ds1);
                ds1.setColor(Color.BLUE);
                ds1.setUnderlineText(false);
            }
        };





        ss.setSpan(clickableSpan1, 0,16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        forgetPass.setText(ss);
        forgetPass.setMovementMethod(LinkMovementMethod.getInstance());


        TextView RegisterHere = findViewById(R.id.txtRegHere);

        String txt = "New Member? Register here";

        SpannableString ss1 = new SpannableString(txt);
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View v) {
                openSignUp();
            }
            @Override
            public void updateDrawState(TextPaint ds){
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
        ss1.setSpan(clickableSpan2, 12,25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        RegisterHere.setText(ss1);
        RegisterHere.setMovementMethod(LinkMovementMethod.getInstance());

    }

    public Boolean validateUsername() {
        String val = loginUsername.getText().toString();
        if (val.isEmpty()) {
            loginUsername.setError("Username cannot be empty");
            return false;
        } else {
            loginUsername.setError(null);
            return true;
        }
    }
    public Boolean validatePassword(){
        String val = loginPassword.getText().toString();
        if (val.isEmpty()) {
            loginPassword.setError("Password cannot be empty");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }
    public void checkUser(){
        String userUsername = loginUsername.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    loginUsername.setError(null);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);
                    if (passwordFromDB.equals(userPassword)) {
                        loginUsername.setError(null);
                        String usernameFromDB = snapshot.child(userUsername).child("username").getValue(String.class);
                        String emailFromDB = snapshot.child(userUsername).child("email").getValue(String.class);
                        String phoneFromDB = snapshot.child(userUsername).child("phone").getValue(String.class);
                        Intent intent = new Intent(LogIn.this, Welcome.class);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("phone", phoneFromDB);
                        intent.putExtra("password", passwordFromDB);
                        startActivity(intent);
                    } else {
                        loginPassword.setError("Invalid Credentials");
                        loginPassword.requestFocus();
                    }
                } else {
                    loginUsername.setError("User does not exist");
                    loginUsername.requestFocus();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }




    public void openWelcome() {
        Intent intent3 = new Intent(this, Welcome.class);
        startActivity(intent3);
    }
    public void openSignUp(){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}
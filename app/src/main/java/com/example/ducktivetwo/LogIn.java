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
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Objects;

public class LogIn extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private FirebaseAuth auth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        auth = FirebaseAuth.getInstance();
        TextView forgetPass;
        forgetPass = findViewById(R.id.txtClickable2);
        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        Button btnLogin2 = (Button) findViewById(R.id.btnLogin2);
        String text = "Forget Password?";

        btnLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();

                if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    if(!password.isEmpty()){
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(LogIn.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LogIn.this, Welcome.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LogIn.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                }else{
                    loginPassword.setError("Required field...");
                }
            }else if(email.isEmpty()){
                loginEmail.setError("Required field...");
            }else{
                loginEmail.setError("Please enter a valid email");
            }
        }});


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
    public void openSignUp(){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}
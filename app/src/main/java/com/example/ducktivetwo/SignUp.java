package com.example.ducktivetwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class SignUp extends AppCompatActivity {
    Button btnReg;
    CheckBox cBox;
    TextView textView;
    EditText signupUname, signupEmail,signupPassword, signupPhone;
    FirebaseDatabase database;
    DatabaseReference reference;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();

        textView = findViewById(R.id.txtClickable);
        btnReg = findViewById(R.id.btnRegister);
        cBox = findViewById(R.id.cbAgree);
        signupUname = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupPhone = findViewById(R.id.signup_no);
        signupPassword = findViewById(R.id.signup_pass);


        btnReg.setEnabled(false);

        cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btnReg.setEnabled(isChecked);
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");


                String username = signupUname.getText().toString().trim();
                String email = signupEmail.getText().toString().trim();
                String password = signupPassword.getText().toString().trim();
                String phone = signupPhone.getText().toString().trim();

                if(username.isEmpty()){
                    signupUname.setError("Required field...");
                }
                if(email.isEmpty()){
                    signupEmail.setError("Required field...");
                }
                if(password.isEmpty()){
                    signupPassword.setError("Required field...");
                }
                if(phone.isEmpty()){
                    signupPhone.setError("Required field...");
                }else{
                    Object nUser = auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser nUser = auth.getCurrentUser();
                                if(nUser!=null){
                                    String id = nUser.getUid();

                                    HelperClass helperClass = new HelperClass(id, username, email, phone, password);
                                    reference.child(id).child("user_data").setValue(helperClass);
                                    new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                                }
                                Toast.makeText(SignUp.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUp.this, LogIn.class));
                            }else{
                                Toast.makeText(SignUp.this, "Sign Up Failed" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


        String text = "Already a member? Log In";

        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View v) {
                openLogIn();
            }
            @Override
            public void updateDrawState(TextPaint ds){
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan1, 18,24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
    public void openLogIn() {
        Intent intent1 = new Intent(this, LogIn.class);
        startActivity(intent1);
    }
}
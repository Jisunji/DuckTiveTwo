package com.example.ducktivetwo;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUp extends AppCompatActivity {
    Button btnReg;
    CheckBox cBox;
    TextView textView;
    EditText signupUname, signupEmail,signupPassword, signupPhone;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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

                String username = signupUname.getText().toString();
                String email = signupEmail.getText().toString();
                String password = signupPassword.getText().toString();
                String phone = signupPhone.getText().toString();

                HelperClass helperClass = new HelperClass(username, email, phone, password);
                reference.child(username).setValue(helperClass);

                Toast.makeText(SignUp.this, "Register Successful", Toast.LENGTH_SHORT).show();
                openLogIn();
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
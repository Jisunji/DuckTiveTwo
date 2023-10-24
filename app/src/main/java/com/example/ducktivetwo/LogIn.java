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
import android.widget.TextView;
import android.widget.Toast;

public class LogIn extends AppCompatActivity {

    private Button btnLogin2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        btnLogin2 = (Button) findViewById(R.id.btnLogin2);
        btnLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIntro();

            }
        });


        TextView forgetpass;
        forgetpass = findViewById(R.id.txtClickable2);

        String text = "Forget Password?";

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

        forgetpass.setText(ss);
        forgetpass.setMovementMethod(LinkMovementMethod.getInstance());

        TextView RegisterHere;
        RegisterHere = findViewById(R.id.txtRegHere);

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
    public void openIntro() {
        Intent intent3 = new Intent(this, Welcome.class);
        startActivity(intent3);
    }
    public void openSignUp(){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}
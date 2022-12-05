package com.example.mobileapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileapp.R;

public class RegisterActivity extends AppCompatActivity {

    TextView btnuser, btnambulan, btnphar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        click();
    }

    private void initView() {
        btnuser = findViewById(R.id.textuser);
        btnambulan = findViewById(R.id.textambulance);
        btnphar = findViewById(R.id.textphar);
    }

    private void click() {
        btnuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, SignUpActivity.class);
                intent.putExtra("roleName", "USER");
                startActivity(intent);
            }
        });

        btnambulan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, SignUpActivity.class);
                intent.putExtra("roleName", "AMBULANCE");
                startActivity(intent);
            }
        });

        btnphar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, SignUpActivity.class);
                intent.putExtra("roleName", "PHARMACY");
                startActivity(intent);
            }
        });
    }

}
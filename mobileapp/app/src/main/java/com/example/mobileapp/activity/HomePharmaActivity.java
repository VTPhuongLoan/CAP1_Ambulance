package com.example.mobileapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileapp.R;
import com.example.mobileapp.activity.pharmacy.PharmacyActivity;
import com.example.mobileapp.activity.pharmacy.PharmacyHistoryPharmacyActivity;
import com.example.mobileapp.activity.pharmacy.ProductListActivity;

public class HomePharmaActivity extends AppCompatActivity {

    TextView btnLogout;
    LinearLayout btnpharma,btnperson,btnbill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pharma);

        initView();
        click();
    }

    private void click() {
        btnpharma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePharmaActivity.this, ProductListActivity.class);
                startActivity(intent);
            }
        });

        btnperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePharmaActivity.this, PharmacyActivity.class);
                startActivity(intent);
            }
        });

        btnbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePharmaActivity.this, PharmacyHistoryPharmacyActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        btnpharma = findViewById(R.id.pharma);
        btnperson = findViewById(R.id.personbtn);
        btnbill = findViewById(R.id.billbtn);
        btnLogout = findViewById(R.id.btnLogout);
    }

}
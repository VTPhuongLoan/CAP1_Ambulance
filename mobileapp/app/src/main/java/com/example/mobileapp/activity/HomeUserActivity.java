package com.example.mobileapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mobileapp.R;
import com.example.mobileapp.activity.user.ShopPharmacyActivity;
import com.example.mobileapp.activity.user.UserBookAmbulanceActivity;
import com.example.mobileapp.activity.user.UserCheckoutActivity;
import com.example.mobileapp.activity.user.UserHistoryBookingActivity;
import com.example.mobileapp.activity.user.UserHistoryPharmacyActivity;

public class HomeUserActivity extends AppCompatActivity {

    LinearLayout btnperson,btnsos,btnloca,btnambu,btnhospital;
    LinearLayout sos;
    ConstraintLayout constraint;

    LinearLayout btnHistoryPharmacy;

    TextView btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);

        initView();
        click();
    }

    private void initView() {
        btnperson = findViewById(R.id.personbtn);
        btnsos = findViewById(R.id.sosbtn);
        btnloca = findViewById(R.id.linearlocation);
        btnambu = findViewById(R.id.linearambulance);
        sos = findViewById(R.id.linearsos);
        constraint = findViewById(R.id.constraint);
        btnhospital = findViewById(R.id.locationbtn);
        btnLogout = findViewById(R.id.btnLogout);
        btnHistoryPharmacy = findViewById(R.id.btnHistoryPharmacy);
    }

    private void click() {
        btnperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intentperson = new Intent(HomeUserActivity.this, DashboardActivity.class);
//                startActivity(intentperson);
            }
        });

        btnsos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intentsos = new Intent(HomeUserActivity.this, NotiActivity.class);
//                startActivity(intentsos);
            }
        });

        btnloca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentloca = new Intent(HomeUserActivity.this, ShopPharmacyActivity.class);
                startActivity(intentloca);
            }
        });

        btnambu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentambu = new Intent(HomeUserActivity.this, UserBookAmbulanceActivity.class);
                startActivity(intentambu);
            }
        });

        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intentso = new Intent(HomeUserActivity.this, SendSOSActivity.class);
//                startActivity(intentso);
            }
        });
        constraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(HomeUserActivity.this, NotiActivity.class);
//                startActivity(intent);
            }
        });
        btnhospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeUserActivity.this, UserHistoryBookingActivity.class);
                startActivity(intent);
            }
        });


        btnHistoryPharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeUserActivity.this, UserHistoryPharmacyActivity.class);
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

}
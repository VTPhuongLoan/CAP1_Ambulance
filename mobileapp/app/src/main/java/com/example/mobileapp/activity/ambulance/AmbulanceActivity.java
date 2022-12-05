package com.example.mobileapp.activity.ambulance;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.mobileapp.R;
import com.example.mobileapp.activity.pharmacy.PharmacyActivity;
import com.example.mobileapp.api.ProfileAPI;
import com.example.mobileapp.dto.ProfileDTO;
import com.example.mobileapp.itf.ProfileInterface;
import com.example.mobileapp.model.Account;
import com.example.mobileapp.util.ContantUtil;

import java.util.List;

public class AmbulanceActivity extends AppCompatActivity implements ProfileInterface {

    TextView textMsg, btnSubmit;
    EditText inputFullName, inputEmail, inputPhone, inputAddress, inputNumberPlate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);

        initView();
        click();

        ProfileAPI profileAPI = new ProfileAPI(AmbulanceActivity.this);
        profileAPI.findProfileByAccountId(Long.parseLong(ContantUtil.authDTO.getAccountId()));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void click() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = inputFullName.getText().toString();
                String email = inputEmail.getText().toString();
                String phone = inputPhone.getText().toString();
                String address = inputAddress.getText().toString();
                String numberPlate = inputNumberPlate.getText().toString();

                ProfileDTO profileDTO = new ProfileDTO();
                profileDTO.setAccountId(Long.parseLong(ContantUtil.authDTO.getAccountId()));
                profileDTO.setFullName(fullname);
                profileDTO.setEmail(email);
                profileDTO.setPhone(phone);
                profileDTO.setAddress(address);
                profileDTO.setNumberPlate(numberPlate);

                ProfileAPI profileAPI = new ProfileAPI(AmbulanceActivity.this);
                profileAPI.updateProfile(profileDTO);
            }
        });
    }

    private void initView() {
        btnSubmit = findViewById(R.id.btnSubmit);
        textMsg = findViewById(R.id.textMsg);
        inputFullName = findViewById(R.id.inputFullName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPhone = findViewById(R.id.inputPhone);
        inputAddress = findViewById(R.id.inputAddress);
        inputNumberPlate= findViewById(R.id.inputNumberPlate);
    }

    private void initData(Account account) {
        inputFullName.setText(account.getFullName());
        inputEmail.setText(account.getEmail());
        inputPhone.setText(account.getPhone());
        inputAddress.setText(account.getAddress());
        inputNumberPlate.setText(account.getNumberPlate());
    }

    @Override
    public void onSuccess(Account account) {
        initData(account);
        Toast.makeText(getApplicationContext(), "Record updated successfully!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(List<String> errors) {
        String msg = "";
        if (errors != null) {
            for (String s : errors) {
                msg += getStringResourceByName(s) + "\n";
            }
        }
        textMsg.setText(msg);
    }

    private String getStringResourceByName(String aString) {
        try {
            String packageName = getPackageName();
            int resId = getResources().getIdentifier(aString, "string", packageName);
            return getString(resId);
        } catch (Exception ex) {
            return "";
        }
    }

}
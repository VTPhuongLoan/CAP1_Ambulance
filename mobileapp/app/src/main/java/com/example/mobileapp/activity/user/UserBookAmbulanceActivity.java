package com.example.mobileapp.activity.user;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.mobileapp.R;
import com.example.mobileapp.activity.LoginActivity;
import com.example.mobileapp.activity.ambulance.AmbulanceBookingSosActivity;
import com.example.mobileapp.api.CheckoutAPI;
import com.example.mobileapp.dto.BookingDTO;
import com.example.mobileapp.itf.CheckoutInterface;
import com.example.mobileapp.model.Orders;
import com.example.mobileapp.util.ContantUtil;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class UserBookAmbulanceActivity extends AppCompatActivity implements CheckoutInterface, LocationListener {

    TextView textLocation;
    Button btncofirm;
    EditText inputDestination, inputCondition;

    protected LocationManager locationManager;
    protected LocationListener locationListener;

    private double longitude;
    private double latitude;
    protected boolean gps_enabled, network_enabled;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5; // 5 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 30 * 1; // 30s

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_book_ambulance);

        initView();

        btncofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String destination = inputDestination.getText().toString();
                String condition = inputCondition.getText().toString();

                if (destination.length() > 0 && condition.length() > 0) {
                    BookingDTO bookingDTO = new BookingDTO();
                    bookingDTO.setAccountId(Long.parseLong(ContantUtil.authDTO.getAccountId()));
                    bookingDTO.setUuid(UUID.randomUUID().toString());
                    bookingDTO.setNote(destination + " - " + condition);

                    ContantUtil.bookingDTO = bookingDTO;

                    CheckoutAPI checkoutAPI = new CheckoutAPI(UserBookAmbulanceActivity.this);
                    checkoutAPI.findBooking(bookingDTO);
                } else {
                    Toast.makeText(UserBookAmbulanceActivity.this, "Please enter destination and condition!", Toast.LENGTH_LONG).show();
                }
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // getting GPS status
        gps_enabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        // getting network status
        network_enabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (gps_enabled) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        } else if (network_enabled) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        };
    }

    private void initView() {
        textLocation = findViewById(R.id.textLocation);
        btncofirm = findViewById(R.id.btnconf);
        inputDestination = findViewById(R.id.inputDestination);
        inputCondition = findViewById(R.id.inputCondition);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onSuccessOrder() {

    }

    @Override
    public void onSuccessBooking() {
        Intent intent = new Intent(getApplicationContext(), AmbulanceBookingSosActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFetchOrders(List<Orders> ordersList) {

    }

    @Override
    public void onFetchBookings() {

    }

    @Override
    public void onError(List<String> errors) {

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("LOG_LOCATION", "Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());

        Geocoder gCoder = new Geocoder(UserBookAmbulanceActivity.this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gCoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && addresses.size() > 0) {
            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            Toast.makeText(getApplicationContext(), "country: " + addresses.get(0).getCountryName(), Toast.LENGTH_LONG).show();

            String ad = address + " " + city + " " + state + " " + country + " - " + postalCode;
            textLocation.setText(ad);
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
//        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
//        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
//        Log.d("Latitude","status");
    }

}
package com.example.mobileapp.activity.ambulance;

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
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.mobileapp.R;
import com.example.mobileapp.activity.HomeUserActivity;
import com.example.mobileapp.activity.user.UserHistoryBookingActivity;
import com.example.mobileapp.api.BookingAPI;
import com.example.mobileapp.api.CheckoutAPI;
import com.example.mobileapp.dto.BookingDTO;
import com.example.mobileapp.itf.BookingInterface;
import com.example.mobileapp.itf.CheckoutInterface;
import com.example.mobileapp.model.Booking;
import com.example.mobileapp.model.Orders;
import com.example.mobileapp.util.ContantUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AmbulanceBookingSosActivity extends AppCompatActivity implements CheckoutInterface, BookingInterface, LocationListener {

    Button btncancel, btnSubmit;
    TextView textTime;

    CountDownTimer countDownTimer = null;

    protected LocationManager locationManager;
    protected LocationListener locationListener;

    private double longitude;
    private double latitude;
    protected boolean gps_enabled, network_enabled;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_sos);

        initView();
        click();

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

        btnSubmit.setVisibility(View.INVISIBLE);

        countDownTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                textTime.setText(String.valueOf(millisUntilFinished / 1000));

                fetchBooking();
            }

            public void onFinish() {
                btnSubmit.setVisibility(View.VISIBLE);
                textTime.setText("NOT FOUND");
            }
        };
        countDownTimer.start();
    }

    private void click() {
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                Intent intent = new Intent(getApplicationContext(), HomeUserActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();

                btnSubmit.setVisibility(View.INVISIBLE);

                BookingDTO bookingDTO = new BookingDTO();
                bookingDTO.setAccountId(Long.parseLong(ContantUtil.authDTO.getAccountId()));
                bookingDTO.setUuid(ContantUtil.bookingDTO.getUuid());
                bookingDTO.setLatitude(latitude);
                bookingDTO.setLongitude(longitude);

                CheckoutAPI checkoutAPI = new CheckoutAPI(AmbulanceBookingSosActivity.this);
                checkoutAPI.findBooking(bookingDTO);

                countDownTimer = new CountDownTimer(60000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        textTime.setText(String.valueOf(millisUntilFinished / 1000));

                        fetchBooking();
                    }

                    public void onFinish() {
                        btnSubmit.setVisibility(View.VISIBLE);
                        textTime.setText("NOT FOUND");
                    }
                };
                countDownTimer.start();
            }
        });
    }

    private void initView() {
        btnSubmit = findViewById(R.id.btnSubmit);
        btncancel = findViewById(R.id.btnCancel);
        textTime = findViewById(R.id.textTime);
    }

    @Override
    public void onSuccessOrder() {

    }

    @Override
    public void onSuccessBooking() {

    }

    @Override
    public void onFetchOrders(List<Orders> ordersList) {

    }

    @Override
    public void onFetchBookings() {

    }

    @Override
    public void onBookingPending() {
        countDownTimer.cancel();
        Intent intent = new Intent(getApplicationContext(), UserHistoryBookingActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onListBooking(List<Booking> bookingList) {

    }

    @Override
    public void onError(List<String> errors) {

    }

    private void fetchBooking() {
        BookingAPI bookingAPI = new BookingAPI(AmbulanceBookingSosActivity.this);
        bookingAPI.findBookingPending(Long.parseLong(ContantUtil.authDTO.getAccountId()));
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Log.d("LOG_LOCATION", "Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());

        Geocoder gCoder = new Geocoder(AmbulanceBookingSosActivity.this, Locale.getDefault());
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
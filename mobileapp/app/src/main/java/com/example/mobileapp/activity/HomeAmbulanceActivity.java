package com.example.mobileapp.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.mobileapp.R;
import com.example.mobileapp.activity.ambulance.AmbulanceActivity;
import com.example.mobileapp.activity.ambulance.AmbulanceHistoryActivity;
import com.example.mobileapp.api.BookingAPI;
import com.example.mobileapp.api.LocationAPI;
import com.example.mobileapp.dto.BookingDTO;
import com.example.mobileapp.dto.LocationDTO;
import com.example.mobileapp.itf.BookingInterface;
import com.example.mobileapp.itf.LocationInterface;
import com.example.mobileapp.model.Booking;
import com.example.mobileapp.util.ContantUtil;

import java.util.List;

public class HomeAmbulanceActivity extends AppCompatActivity implements LocationInterface, LocationListener, BookingInterface {

    TextView textNumberCar, textPoint, textLocation, btnViewList, btnLogout, btnMap;
    ImageView btnmenu;
    Switch switchLocation;

    CountDownTimer countDownTimer = null;

    protected LocationManager locationManager;
    protected LocationListener locationListener;

    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5; // 5 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 30 * 1; // 30s

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_ambulance);

        initView();
        click();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        switchLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                LocationDTO locationDTO = new LocationDTO();
                locationDTO.setAccountId(Long.parseLong(ContantUtil.authDTO.getAccountId()));
                locationDTO.setStatus(isChecked);

                LocationAPI locationAPI = new LocationAPI(HomeAmbulanceActivity.this);
                locationAPI.updateStatus(locationDTO);
            }
        });

        // booking api
        BookingAPI bookingAPI = new BookingAPI(HomeAmbulanceActivity.this);
        bookingAPI.findAllBookingByAmbulanceAndProgress(Long.parseLong(ContantUtil.authDTO.getAccountId()));

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
        }
        ;

    }

    private void click() {
        btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeAmbulanceActivity.this, AmbulanceActivity.class);
                startActivity(intent);
            }
        });

        btnViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeAmbulanceActivity.this, AmbulanceHistoryActivity.class);
                startActivity(intent);
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeAmbulanceActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        textNumberCar.setText("License plates: " + ContantUtil.authDTO.getNumberPlate());

    }

    private void initView() {
        btnViewList = findViewById(R.id.btnViewList);
        textNumberCar = findViewById(R.id.textNumberCar);
        textPoint = findViewById(R.id.textPoint);
        textLocation = findViewById(R.id.textLocation);
        btnmenu = findViewById(R.id.menu);
        switchLocation = findViewById(R.id.switchLocation);
        btnLogout = findViewById(R.id.btnLogout);
        btnMap = findViewById(R.id.btnMap);
    }

    @Override
    public void onSuccessLocation() {

    }

    @Override
    public void onBookingPending() {

    }

    @Override
    public void onListBooking(List<Booking> bookingList) {
        if (bookingList != null && !bookingList.isEmpty()) {
            Booking booking = bookingList.get(0);
            textPoint.setText(booking.getAccountDTO().getFullName() + " - " + booking.getAccountDTO().getPhone());
            textLocation.setText(booking.getNote());
        }
    }

    @Override
    public void onError(List<String> errors) {

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String title = intent.getExtras().getString("title");
            String body = intent.getExtras().getString("body");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // show message
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeAmbulanceActivity.this);
                    builder.setMessage(body).setTitle("Ambulance Booking");

                    // Setting message manually and performing action on button click
                    builder.setMessage(body)
                            .setCancelable(false)
                            .setPositiveButton("I agree", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    BookingDTO bookingDTO = new BookingDTO();
                                    bookingDTO.setHistoryId(Long.parseLong(title));
                                    bookingDTO.setAccountId(Long.parseLong(ContantUtil.authDTO.getAccountId()));
                                    bookingDTO.setProgress("ACCEPTED");

                                    BookingAPI bookingAPI = new BookingAPI(HomeAmbulanceActivity.this);
                                    bookingAPI.saveBooking(bookingDTO);
                                }
                            })
                            .setNegativeButton("Skip", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    BookingDTO bookingDTO = new BookingDTO();
                                    bookingDTO.setHistoryId(Long.parseLong(title));
                                    bookingDTO.setAccountId(Long.parseLong(ContantUtil.authDTO.getAccountId()));
                                    bookingDTO.setProgress("CANCELED");

                                    BookingAPI bookingAPI = new BookingAPI(HomeAmbulanceActivity.this);
                                    bookingAPI.saveBooking(bookingDTO);

                                    dialog.cancel();
                                }
                            });
                    // Creating dialog box
                    AlertDialog alert = builder.create();
                    // Setting the title manually
                    alert.setTitle("Ambulance Booking");
                    alert.show();
                }
            });

            countDownTimer = new CountDownTimer(60000, 1000) {
                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    countDownTimer.cancel();
                }
            };
            countDownTimer.start();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((mMessageReceiver),
                new IntentFilter("MyMessage")
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("LOG_LOCATION", "Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
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
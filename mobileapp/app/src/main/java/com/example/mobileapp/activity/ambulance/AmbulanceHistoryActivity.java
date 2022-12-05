package com.example.mobileapp.activity.ambulance;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.adapter.BookingAdapter;
import com.example.mobileapp.api.BookingAPI;
import com.example.mobileapp.itf.BookingInterface;
import com.example.mobileapp.model.Booking;
import com.example.mobileapp.util.ContantUtil;

import java.util.List;

public class AmbulanceHistoryActivity extends AppCompatActivity implements BookingInterface {

    RecyclerView recyclerViewProductList;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history_pharmacy);

        BookingAPI bookingAPI = new BookingAPI(AmbulanceHistoryActivity.this);
        bookingAPI.findAllBookingByAmbulance(Long.parseLong(ContantUtil.authDTO.getAccountId()));
    }


    @Override
    public void onBookingPending() {

    }

    @Override
    public void onListBooking(List<Booking> bookingList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewProductList = findViewById(R.id.recyclerViewSearch);
        recyclerViewProductList.setLayoutManager(linearLayoutManager);

        adapter = new BookingAdapter(getApplicationContext(), getLayoutInflater(), bookingList, true);
        recyclerViewProductList.setAdapter(adapter);
    }

    @Override
    public void onError(List<String> errors) {

    }

}
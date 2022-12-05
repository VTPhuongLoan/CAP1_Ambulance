package com.example.mobileapp.activity.pharmacy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.activity.HomeAmbulanceActivity;
import com.example.mobileapp.adapter.ShopOrderAdapter;
import com.example.mobileapp.api.CheckoutAPI;
import com.example.mobileapp.itf.CheckoutInterface;
import com.example.mobileapp.model.Orders;
import com.example.mobileapp.util.ContantUtil;

import java.util.List;

public class PharmacyHistoryPharmacyActivity extends AppCompatActivity implements CheckoutInterface {

    RecyclerView recyclerViewProductList;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history_pharmacy);

        CheckoutAPI checkoutAPI = new CheckoutAPI(PharmacyHistoryPharmacyActivity.this);
        checkoutAPI.findAllOrdersByPharmacy(Long.parseLong(ContantUtil.authDTO.getPharmacyId()));
    }

    @Override
    public void onSuccessOrder() {

    }

    @Override
    public void onSuccessBooking() {

    }

    @Override
    public void onFetchOrders(List<Orders> ordersList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewProductList = findViewById(R.id.recyclerViewSearch);
        recyclerViewProductList.setLayoutManager(linearLayoutManager);

        adapter = new ShopOrderAdapter(getApplicationContext(), ordersList, false);
        recyclerViewProductList.setAdapter(adapter);
    }

    @Override
    public void onFetchBookings() {

    }

    @Override
    public void onError(List<String> errors) {

    }

}
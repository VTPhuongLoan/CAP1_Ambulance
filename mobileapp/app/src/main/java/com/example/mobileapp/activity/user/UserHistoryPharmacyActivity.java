package com.example.mobileapp.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.adapter.ShopOrderAdapter;
import com.example.mobileapp.adapter.ShopProductAdapter;
import com.example.mobileapp.api.CheckoutAPI;
import com.example.mobileapp.api.ProductAPI;
import com.example.mobileapp.itf.CheckoutInterface;
import com.example.mobileapp.itf.ProductInterface;
import com.example.mobileapp.model.Orders;
import com.example.mobileapp.model.Product;
import com.example.mobileapp.util.ContantUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class UserHistoryPharmacyActivity extends AppCompatActivity implements CheckoutInterface {

    RecyclerView recyclerViewProductList;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history_pharmacy);

        CheckoutAPI checkoutAPI = new CheckoutAPI(UserHistoryPharmacyActivity.this);
        checkoutAPI.findAllOrdersByAccount(Long.parseLong(ContantUtil.authDTO.getAccountId()));

//        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.btnViewCart);
//        myFab.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), ShopCartActivity.class);
//                startActivity(i);
//            }
//        });
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
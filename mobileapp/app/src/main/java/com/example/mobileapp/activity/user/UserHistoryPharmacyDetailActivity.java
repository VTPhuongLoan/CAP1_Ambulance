package com.example.mobileapp.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.activity.HomeAmbulanceActivity;
import com.example.mobileapp.activity.HomePharmaActivity;
import com.example.mobileapp.adapter.ShopOrderAdapter;
import com.example.mobileapp.adapter.ShopOrderDetailAdapter;
import com.example.mobileapp.api.CheckoutAPI;
import com.example.mobileapp.dto.OrdersDTO;
import com.example.mobileapp.itf.CheckoutInterface;
import com.example.mobileapp.model.OrderDetails;
import com.example.mobileapp.model.Orders;
import com.example.mobileapp.util.ContantUtil;

import java.util.List;

public class UserHistoryPharmacyDetailActivity extends AppCompatActivity implements CheckoutInterface {

    RecyclerView recyclerViewProductList;
    RecyclerView.Adapter adapter;

    TextView textTotal, textName;
    TextView btnComplete, btnCancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history_pharmacy_detail);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewProductList = findViewById(R.id.recyclerView);
        recyclerViewProductList.setLayoutManager(linearLayoutManager);

        List<OrderDetails> ordersList = ContantUtil.orders.getOrderDetails();

        adapter = new ShopOrderDetailAdapter(getApplicationContext(), ordersList, false);
        recyclerViewProductList.setAdapter(adapter);

        Orders orders = ContantUtil.orders;

        textTotal = findViewById(R.id.textTotal);
        textTotal.setText(String.valueOf(orders.getTotalCost()));

        String name = orders.getPharmacyDTO().getName() + " - " + orders.getCreatedOn();
        textName= findViewById(R.id.textName);
        textName.setText(name);

        btnComplete = findViewById(R.id.btnComplete);
        btnCancle = findViewById(R.id.btnCancle);

        String getPharmacyId = ContantUtil.authDTO.getPharmacyId();
        if (getPharmacyId == null) {
            getPharmacyId = "";
        }
        if (getPharmacyId.equalsIgnoreCase("") || Long.parseLong(getPharmacyId) <= 0) {
            btnComplete.setVisibility(View.INVISIBLE);
            btnCancle.setVisibility(View.INVISIBLE);
        }

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // order
                OrdersDTO ordersDTO = new OrdersDTO();
                ordersDTO.setId(ContantUtil.orders.getId());
                ordersDTO.setProgress("COMPLETED");

                CheckoutAPI checkoutAPI = new CheckoutAPI(UserHistoryPharmacyDetailActivity.this);
                checkoutAPI.updateOrderProgress(ordersDTO);
            }
        });

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // order
                OrdersDTO ordersDTO = new OrdersDTO();
                ordersDTO.setId(ContantUtil.orders.getId());
                ordersDTO.setProgress("CANCLED");

                CheckoutAPI checkoutAPI = new CheckoutAPI(UserHistoryPharmacyDetailActivity.this);
                checkoutAPI.updateOrderProgress(ordersDTO);
            }
        });
    }


    @Override
    public void onSuccessOrder() {
        Intent intent = new Intent(getApplicationContext(), HomePharmaActivity.class);
        startActivity(intent);
        finish();
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
    public void onError(List<String> errors) {

    }
}
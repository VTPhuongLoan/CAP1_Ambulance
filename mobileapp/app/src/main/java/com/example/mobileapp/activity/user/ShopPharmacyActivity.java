package com.example.mobileapp.activity.user;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.adapter.PharmacyAdapter;
import com.example.mobileapp.api.PharmacyAPI;
import com.example.mobileapp.itf.PharmacyInterface;
import com.example.mobileapp.model.Pharmacy;

import java.util.List;

public class ShopPharmacyActivity extends AppCompatActivity implements PharmacyInterface {

    RecyclerView recyclerViewProductList;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        PharmacyAPI pharmacyAPI = new PharmacyAPI(ShopPharmacyActivity.this);
        pharmacyAPI.findAllPharmacy();
    }

    @Override
    public void onSuccess(List<Pharmacy> pharmacyList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewProductList = findViewById(R.id.recyclerViewSearch);
        recyclerViewProductList.setLayoutManager(linearLayoutManager);

        adapter = new PharmacyAdapter(getApplicationContext(), pharmacyList);
        recyclerViewProductList.setAdapter(adapter);
    }

    @Override
    public void onError(List<String> errors) {

    }

}
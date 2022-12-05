package com.example.mobileapp.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.adapter.ShopProductAdapter;
import com.example.mobileapp.api.ProductAPI;
import com.example.mobileapp.itf.ProductInterface;
import com.example.mobileapp.model.Product;
import com.example.mobileapp.util.ContantUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ShopProductActivity extends AppCompatActivity implements ProductInterface {

    RecyclerView recyclerViewProductList;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_product_list);

        Intent intent = getIntent();
        long pharmacyId = intent.getLongExtra("pharmacyId", 0);
        ContantUtil.pharmacyId = pharmacyId;

        ProductAPI productAPI = new ProductAPI(ShopProductActivity.this);
        productAPI.findAllProductByPharmacy(pharmacyId);

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.btnViewCart);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ShopCartActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onSuccess(List<Product> productList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewProductList = findViewById(R.id.recyclerViewSearch);
        recyclerViewProductList.setLayoutManager(linearLayoutManager);

        adapter = new ShopProductAdapter(ShopProductActivity.this, getApplicationContext(), productList);
        recyclerViewProductList.setAdapter(adapter);
    }

    @Override
    public void onError(List<String> errors) {

    }

}
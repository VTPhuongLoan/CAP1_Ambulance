package com.example.mobileapp.activity.pharmacy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.adapter.ProductAdapter;
import com.example.mobileapp.api.ProductAPI;
import com.example.mobileapp.itf.ProductInterface;
import com.example.mobileapp.model.Product;
import com.example.mobileapp.util.ContantUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import com.example.mobileapp.R;

public class ProductListActivity extends AppCompatActivity implements ProductInterface {

    RecyclerView recyclerViewProductList;
    RecyclerView.Adapter adapter;
    FloatingActionButton addProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        initView();
        click();

        ProductAPI productAPI = new ProductAPI(ProductListActivity.this);
        productAPI.findAllProductByPharmacy(Long.parseLong(ContantUtil.authDTO.getPharmacyId()));
    }

    private void click() {
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductListActivity.this, ProductFormActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("pharmacyId", Long.parseLong(ContantUtil.authDTO.getPharmacyId()));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        addProduct = findViewById(R.id.product_add);
    }

    private void recyclerViewProduct(List<Product> productList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewProductList = findViewById(R.id.recyclerViewSearch);
        recyclerViewProductList.setLayoutManager(linearLayoutManager);
        recyclerViewProductList.setAdapter(adapter);
    }

    @Override
    public void onSuccess(List<Product> productList) {
        recyclerViewProduct(productList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewProductList = findViewById(R.id.recyclerViewSearch);
        recyclerViewProductList.setLayoutManager(linearLayoutManager);

        adapter = new ProductAdapter(ProductListActivity.this, productList);
        recyclerViewProductList.setAdapter(adapter);
    }

    @Override
    public void onError(List<String> errors) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        ProductAPI productAPI = new ProductAPI(ProductListActivity.this);
        productAPI.findAllProductByPharmacy(Long.parseLong(ContantUtil.authDTO.getPharmacyId()));
    }

}
package com.example.mobileapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.activity.pharmacy.PharmacyHistoryPharmacyActivity;
import com.example.mobileapp.activity.user.UserHistoryPharmacyDetailActivity;
import com.example.mobileapp.api.CheckoutAPI;
import com.example.mobileapp.dto.OrdersDTO;
import com.example.mobileapp.itf.CheckoutInterface;
import com.example.mobileapp.model.Orders;
import com.example.mobileapp.model.Product;
import com.example.mobileapp.util.ContantUtil;

import java.util.List;

public class ShopOrderAdapter extends RecyclerView.Adapter<ShopOrderAdapter.ProductViewhoder> {

    Context context;
    List<Orders> list;
    boolean isUpdate;

    public ShopOrderAdapter(Context context, List<Orders> list, boolean isUpdate) {
        this.context = context;
        this.list = list;
        this.isUpdate = isUpdate;

    }

    @NonNull
    @Override
    public ProductViewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_shop_order, parent, false);
        return new ProductViewhoder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewhoder holder, @SuppressLint("RecyclerView") int position) {
        Orders orders = list.get(position);

        holder.textName.setText(orders.getPharmacyDTO().getName());
        holder.textQty.setText(String.valueOf(orders.getTotalCost()));
        holder.textDate.setText(String.valueOf(orders.getCreatedOn()));
        holder.textTotal.setText(String.valueOf(orders.getProgress()));

        holder.main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ContantUtil.orders = orders;
                    Intent intent = new Intent(context, UserHistoryPharmacyDetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
//
//        if (isUpdate) {
//            holder.btnAdd.setVisibility(View.VISIBLE);
//            holder.btnRemove.setVisibility(View.VISIBLE);
//
//            holder.btnAdd.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    ContantUtil.addToCart(product.getId(), product);
//                    Product p = ContantUtil.getProduct(product.getId());
//                    if (p != null) {
//                        product.setQty(p.getQty());
//                        holder.textQty.setText(String.valueOf(product.getQty()));
//
//                        double total = Double.parseDouble(product.getPrice()) * product.getQty();
//                        holder.textTotal.setText(String.valueOf(total));
//                    }
//                }
//            });
//
//            holder.btnRemove.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    ContantUtil.removeToCart(product.getId());
//                    Product p = ContantUtil.getProduct(product.getId());
//                    if (p != null) {
//                        product.setQty(p.getQty());
//                        holder.textQty.setText(String.valueOf(product.getQty()));
//
//                        double total = Double.parseDouble(product.getPrice()) * product.getQty();
//                        holder.textTotal.setText(String.valueOf(total));
//                    }
//                }
//            });
//        } else {
//            holder.btnAdd.setVisibility(View.INVISIBLE);
//            holder.btnRemove.setVisibility(View.INVISIBLE);
//        }
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public class ProductViewhoder extends RecyclerView.ViewHolder {

        ConstraintLayout main;
        TextView textName, textDate, textQty, textTotal;
        ImageView btnRemove, btnAdd;



        public ProductViewhoder(@NonNull View itemView) {
            super(itemView);
            main = itemView.findViewById(R.id.mainPopular);
            textName = itemView.findViewById(R.id.textName);
            textDate = itemView.findViewById(R.id.textDate);
            textQty = itemView.findViewById(R.id.textQty);
            textTotal = itemView.findViewById(R.id.textTotal);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            btnAdd = itemView.findViewById(R.id.btnAdd);

        }
    }
}
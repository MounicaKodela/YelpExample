package com.rbc.yelp.services.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rbc.yelp.R;
import com.rbc.yelp.services.models.Business;
import com.rbc.yelp.services.view.BusinessDetailsActivity;

import java.util.List;

public class BusinessViewAdapter extends RecyclerView.Adapter<BusinessViewAdapter.MyViewHolder> {
    public List<Business> businessesList;
    Context cxt;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView businessName;

        public MyViewHolder(View itemView) {
            super(itemView);
            businessName = itemView.findViewById(R.id.businessName);
        }
    }

    public BusinessViewAdapter(List<Business> arrayList, Context mContext) {
        this.cxt = mContext;
        this.businessesList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.businesses_card_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String currentBusinessName = businessesList.get(position).getName();
        holder.businessName.setText(currentBusinessName);
        holder.businessName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent businessDetailsActivity = new Intent(cxt, BusinessDetailsActivity.class);
                businessDetailsActivity.putExtra("businessId", businessesList.get(position).getId());
                v.getContext().startActivity(businessDetailsActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return businessesList.size();
    }
}





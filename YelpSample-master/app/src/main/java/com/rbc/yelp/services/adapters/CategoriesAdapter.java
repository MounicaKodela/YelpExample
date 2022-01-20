package com.rbc.yelp.services.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rbc.yelp.R;
import com.rbc.yelp.services.models.Business;

import java.util.HashMap;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {
    private HashMap<String, List<Business>> categoryMap;
    public Context cxt;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryName;
        public RecyclerView businessRecyclerView;

        public MyViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            businessRecyclerView = itemView.findViewById(R.id.businessRecyclerView);
        }
    }

    public CategoriesAdapter(HashMap<String, List<Business>> map, Context context) {
        this.categoryMap = map;
        this.cxt = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return categoryMap.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        String currentCategory = categoryMap.keySet().toArray()[position].toString();
        List<Business> businessList = categoryMap.get(currentCategory);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(cxt);
        holder.businessRecyclerView.setLayoutManager(layoutManager);
        // TODO: 19-01-2022 Remove hardcoding
        holder.categoryName.setText(currentCategory + "("+businessList.size()+")");

        BusinessViewAdapter businessViewAdapter = new BusinessViewAdapter(businessList, holder.businessRecyclerView.getContext());
        holder.businessRecyclerView.setAdapter(businessViewAdapter);
    }
}

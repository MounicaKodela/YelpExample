package com.rbc.yelp.services.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import com.rbc.yelp.databinding.ActivitySearchBinding;
import com.rbc.yelp.services.viewmodels.SearchViewModel;
import com.rbc.yelp.services.models.Business;

import java.util.HashMap;
import java.util.List;

import com.rbc.yelp.services.adapters.CategoriesAdapter;

public class SearchActivity extends AppCompatActivity {

    SearchViewModel searchViewModel  = new SearchViewModel();
    ActivitySearchBinding activitySearchBinding;
    HashMap<String, List<Business>> lcategoryMap = new HashMap<>();
    private LinearLayoutManager layoutManager;
    private CategoriesAdapter categoryAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding = ActivitySearchBinding.inflate(getLayoutInflater());
        progressDialog = new ProgressDialog(this);
        layoutManager = new LinearLayoutManager(this);
        /*
        * Note: Add Location services to determine location. For now showing results for Vancouver
        * */
        activitySearchBinding.location.setText("Showing results for Vancouver");
        activitySearchBinding.location.setVisibility(View.GONE);
        activitySearchBinding.searchView.setQueryHint("Search for Business/ Category");
        activitySearchBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                setLayout(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        setContentView(activitySearchBinding.getRoot());
    }

    private void setLayout(String query) {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        searchViewModel.getSearchData(query, "Vancouver").observe(SearchActivity.this, new Observer<HashMap<String, List<Business>>>() {

            @Override
            public void onChanged(HashMap<String, List<Business>> categoryMap) {
                progressDialog.cancel();
                activitySearchBinding.location.setVisibility(View.VISIBLE);
                lcategoryMap = categoryMap;
                categoryAdapter = new CategoriesAdapter(lcategoryMap, SearchActivity.this);
                activitySearchBinding.recyclerView .setLayoutManager(layoutManager);
                activitySearchBinding.recyclerView .setAdapter(categoryAdapter);

                if(categoryMap.size()<0) {
                    activitySearchBinding.location.setText("No Results Found");
                }
            }
        });
    }
}
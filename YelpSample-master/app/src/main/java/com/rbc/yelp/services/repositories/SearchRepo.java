package com.rbc.yelp.services.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.rbc.yelp.services.MyApplication;
import com.rbc.yelp.services.YelpApi;
import com.rbc.yelp.services.YelpRetrofit;
import com.rbc.yelp.services.models.Business;
import com.rbc.yelp.services.models.Category;
import com.rbc.yelp.services.models.SearchResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRepo {

    private final String TAG = getClass().getSimpleName();

    public MutableLiveData<HashMap<String, List<Business>>> requestData(String search, String location) {
        final MutableLiveData<HashMap<String, List<Business>>> mutableLiveData = new MutableLiveData<>();

        YelpApi apiService = new YelpRetrofit()
                .getRetrofitInstance()
                .create(YelpApi.class);

        apiService.search(search, location, "best_match", 20).enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                Log.e(TAG, "response="+response.errorBody());

                if (response.isSuccessful() && response.body()!=null ) {
                    Log.e(TAG, "response.size="+response.body().getTotal());
                    HashMap<String, List<Business>> categoryMap = getCategoryMap(response.body());
                    mutableLiveData.setValue(categoryMap);
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                Log.e(TAG, "onFailure" + call.toString());
                HashMap<String, List<Business>> categoryMap = new HashMap<>();
                mutableLiveData.setValue(categoryMap);
            }
        });

        return mutableLiveData;
    }

    /**
     * @param searchResults
     * @return categoryMap
     *
     * method to filter and group results based on category
     */
    private HashMap<String, List<Business>> getCategoryMap(SearchResult searchResults) {
        HashMap<String, List<Business>> categoryMap = new HashMap<>();
        for(int i=0; i<searchResults.getBusinesses().size(); i++) {
            int categorySize = searchResults.getBusinesses().get(i).getCategories().size();
            for(int j=0; j<categorySize; j++) {
                List<Business> lcategoryMap;
                String categoryName = searchResults.getBusinesses().get(i).getCategories().get(j).getTitle();
                if (categoryMap.containsKey(categoryName)){
                    lcategoryMap = categoryMap.get(categoryName);
                    lcategoryMap.add(searchResults.getBusinesses().get(i));
                    categoryMap.put(categoryName, lcategoryMap);
                } else {
                    lcategoryMap = new ArrayList<>();
                    lcategoryMap.add(searchResults.getBusinesses().get(i));
                    categoryMap.put(categoryName, lcategoryMap);
                }
            }
        }

        return categoryMap;
    }
}

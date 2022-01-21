package com.rbc.yelp.services.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.rbc.yelp.services.YelpApi;
import com.rbc.yelp.services.YelpRetrofit;
import com.rbc.yelp.services.models.BusinessDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessDetailsRepo {

    private final String TAG = getClass().getSimpleName();

    public MutableLiveData<BusinessDetails> getBusinessDetails(String businessId) {
        final MutableLiveData<BusinessDetails> mutableLiveData = new MutableLiveData<>();

        YelpApi apiService = new YelpRetrofit()
                .getRetrofitInstance()
                .create(YelpApi.class);

        apiService.getBusinessDetails(businessId).enqueue(new Callback<BusinessDetails>() {
            @Override
            public void onResponse(Call<BusinessDetails> call, Response<BusinessDetails> response) {
                Log.e(TAG, "response=" + response.errorBody());

                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "response=" + response.body().getName());
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BusinessDetails> call, Throwable t) {
                Log.e(TAG, "onFailure" + call.toString());
            }
        });

        return mutableLiveData;
    }
}

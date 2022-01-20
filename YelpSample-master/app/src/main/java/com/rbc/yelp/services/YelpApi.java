package com.rbc.yelp.services;

import com.rbc.yelp.services.models.BusinessDetails;
import com.rbc.yelp.services.models.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface YelpApi {
    @GET("/v3/businesses/search")
    Call<SearchResult> search(@Query("term") String term, @Query("location") String location, @Query("sort_by") String sortBy, @Query("limit") int limit);

    @GET("/v3/businesses/{id}")
    Call<BusinessDetails> getBusinessDetails(@Path("id") String businessId);
}



package com.rbc.yelp.services.viewmodels;

import java.util.HashMap;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rbc.yelp.services.repositories.SearchRepo;
import com.rbc.yelp.services.models.Business;


public class SearchViewModel extends ViewModel {

    private SearchRepo searchRepo;
    public MutableLiveData<HashMap<String, List<Business>>> mutableLiveData;

    public SearchViewModel() {
        searchRepo = new SearchRepo();
    }

    public LiveData<HashMap<String, List<Business>>> getSearchData(String search, String location) {
        mutableLiveData = searchRepo.requestData(search, location);
        return mutableLiveData;
    }

}

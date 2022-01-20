package com.rbc.yelp.services.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rbc.yelp.services.models.BusinessDetails;
import com.rbc.yelp.services.repositories.BusinessDetailsRepo;

public class BusinessDetailsViewModel extends ViewModel {
    private BusinessDetailsRepo businessDetailsRepo;
    private MutableLiveData<BusinessDetails> mutableLiveData;

    public BusinessDetailsViewModel() {
        businessDetailsRepo = new BusinessDetailsRepo();
    }

    public LiveData<BusinessDetails> getBusinessDetails(String businessId) {
        mutableLiveData = businessDetailsRepo.getBusinessDetails(businessId);
        return mutableLiveData;
    }
}

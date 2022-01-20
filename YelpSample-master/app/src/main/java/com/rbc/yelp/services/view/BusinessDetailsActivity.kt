package com.rbc.yelp.services.view

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rbc.yelp.databinding.ActivityBusinessDetailsBinding
import com.rbc.yelp.services.models.BusinessDetails
import com.rbc.yelp.services.viewmodels.BusinessDetailsViewModel

class BusinessDetailsActivity : AppCompatActivity() {

    private lateinit var businessDetailsViewModel: BusinessDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBusinessDetailsBinding.inflate(layoutInflater)
        val businessId = intent.getStringExtra("businessId")
        businessDetailsViewModel = ViewModelProvider(this).get(BusinessDetailsViewModel::class.java)

        businessDetailsViewModel.getBusinessDetails(businessId).observe(this, Observer {
            binding.businessNameHeader.text = it.name
            binding.rating.text = it.rating.toString()
            binding.totalReviews.text = it.reviewCount.toString() + " Reviews"
            binding.isOpen.text = if (it.hours[0].isOpenNow) "Open Now" else "Closed"
            Glide.with(this)
                .load(it.imageUrl)
                .into(binding.imageView)
            var categorytext = "Categories: "
            for(index in it.categories.indices) {
                categorytext = categorytext+it.categories.get(index).title
                if(index < it.categories.size-1) {
                    categorytext = categorytext+", "
                }
            }
            binding.categories.text = categorytext
            binding.phone.text = it.displayPhone
            binding.location.text = it.location.city
        })


        setContentView(binding.root)
    }
}
package com.erickpimentel.marvelapp

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MarvelApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        // Initialize Glide with custom options if needed
        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL) // You can customize caching strategy as per your needs
//            .placeholder(R.drawable.placeholder_image) // Placeholder image resource
//            .error(R.drawable.error_image) // Error image resource

        Glide.init(this, GlideBuilder().setDefaultRequestOptions(requestOptions))
    }
}
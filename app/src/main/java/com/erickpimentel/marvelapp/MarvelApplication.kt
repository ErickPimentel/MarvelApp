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

        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        Glide.init(this, GlideBuilder().setDefaultRequestOptions(requestOptions))
    }
}
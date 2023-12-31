package com.erickpimentel.marvelapp.data.di

import android.util.Log
import com.erickpimentel.marvelapp.data.api.MarvelApiService
import com.erickpimentel.marvelapp.data.repository.MarvelRepositoryImpl
import com.erickpimentel.marvelapp.data.repository.MarvelRepository
import com.erickpimentel.marvelapp.data.network.AuthInterceptor
import com.erickpimentel.marvelapp.data.network.AuthInterceptor.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideBaseUrl(): String = BASE_URL

    @Provides
    @Singleton
    fun provideAuthInterceptor(): AuthInterceptor = AuthInterceptor()

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor { message -> Log.d("OkHttp", message) }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        httpClientBuilder.addInterceptor(loggingInterceptor)

        httpClientBuilder.addInterceptor(authInterceptor)

        return httpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideMarvelApiService(retrofit: Retrofit): MarvelApiService =
        retrofit.create(MarvelApiService::class.java)

    @Provides
    @Singleton
    fun provideMarvelRepository(marvelApiService: MarvelApiService): MarvelRepository {
        return MarvelRepositoryImpl(marvelApiService)
    }
}
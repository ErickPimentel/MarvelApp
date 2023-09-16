package com.erickpimentel.marvelapp.data.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.erickpimentel.marvelapp.data.local.database.CharacterDataBase
import com.erickpimentel.marvelapp.data.remote.api.CharacterApiService
import com.erickpimentel.marvelapp.data.remote.repository.MarvelRepositoryImpl
import com.erickpimentel.marvelapp.data.remote.repository.MarvelRepository
import com.erickpimentel.marvelapp.data.remote.network.AuthInterceptor
import com.erickpimentel.marvelapp.data.remote.network.AuthInterceptor.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideMarvelApiService(retrofit: Retrofit): CharacterApiService =
        retrofit.create(CharacterApiService::class.java)

    @Provides
    @Singleton
    fun provideMarvelRepository(characterApiService: CharacterApiService): MarvelRepository {
        return MarvelRepositoryImpl(characterApiService)
    }
}
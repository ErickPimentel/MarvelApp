package com.erickpimentel.marvelapp.data.di

import com.erickpimentel.marvelapp.data.api.MarvelApiService
import com.erickpimentel.marvelapp.data.repository.MarvelRepositoryImpl
import com.erickpimentel.marvelapp.domain.repository.MarvelRepository
import com.erickpimentel.marvelapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()

        val authorizationInterceptor = Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()

//            val decryptedToken = keyStoreUtils.decryptToken(
//                keyStoreUtils.getEncryptedToken(context),
//                keyStoreUtils.generateOrRetrieveSecretKey()
//            )
//
//            requestBuilder.addHeader("Authorization", "Bearer $decryptedToken")

            chain.proceed(requestBuilder.build())
        }
        httpClientBuilder.addInterceptor(authorizationInterceptor)


        return httpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
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
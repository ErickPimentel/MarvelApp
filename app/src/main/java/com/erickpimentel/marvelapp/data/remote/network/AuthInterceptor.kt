package com.erickpimentel.marvelapp.data.remote.network

import okhttp3.Interceptor
import okhttp3.Response
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

class AuthInterceptor : Interceptor {

    companion object {
        private const val API_PARAM = "&apikey="
        private const val HASH_PARAM = "&hash="
        private const val TS_PARAM = "&ts="

        const val BASE_URL = "https://gateway.marvel.com"
        private const val PUBLIC_KEY = "ca64adc2c62c2c6c95f5f5cba57b5500"
        private const val PRIVATE_KEY = "f00c832c74c276a27d9462d1e5c50c0a7dc39e9c"
        private val timeStamp = Timestamp(System.currentTimeMillis()).time.toString()
    }

    private fun hash(): String {
        val input = "$timeStamp$PRIVATE_KEY$PUBLIC_KEY"
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val newUrl = buildString {
            append(request.url)
            append("$API_PARAM$PUBLIC_KEY")
            append("$HASH_PARAM${hash()}")
            append("$TS_PARAM$timeStamp")
        }

        request = request.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(request)
    }
}
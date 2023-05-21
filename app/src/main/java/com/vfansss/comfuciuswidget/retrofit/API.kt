package com.vfansss.comfuciuswidget.retrofit

import com.vfansss.comfuciuswidget.AppConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object API {
    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(30L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .client(httpClient)
        .baseUrl(AppConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val quoteService = retrofit.create(QuoteService::class.java)
}
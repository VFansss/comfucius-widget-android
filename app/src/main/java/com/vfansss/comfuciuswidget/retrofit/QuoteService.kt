package com.vfansss.comfuciuswidget.retrofit

import com.vfansss.comfuciuswidget.AppConfig
import retrofit2.Call
import retrofit2.http.GET

interface QuoteService {
    @GET(AppConfig.QUOTE_URL)
    fun getQuote(): Call<Quote>
}
package com.vfansss.comfuciuswidget

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.italic
import com.vfansss.comfuciuswidget.databinding.ActivityMainBinding
import com.vfansss.comfuciuswidget.retrofit.API
import com.vfansss.comfuciuswidget.retrofit.Quote
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        API.quoteService.getQuote().enqueue(object : Callback<Quote> {
            override fun onResponse(call: Call<Quote>, response: Response<Quote>) {
                if (response.isSuccessful && response.body() != null) {
                    val quote = response.body()!!
                    binding.quote.text = buildSpannedString {
                        italic { append(quote.phrase) }
                        bold { append(" ~ ${quote.thinker}") }
                    }
                }
            }

            override fun onFailure(call: Call<Quote>, t: Throwable) {}
        })

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(AppConfig.BASE_URL))
        binding.root.setOnClickListener { startActivity(intent) }
    }
}
package com.vfansss.comfuciuswidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.italic
import com.vfansss.comfuciuswidget.AppConfig.backgrounds
import com.vfansss.comfuciuswidget.retrofit.API
import com.vfansss.comfuciuswidget.retrofit.Quote
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuoteWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {}
    override fun onDisabled(context: Context) {}
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val views = RemoteViews(context.packageName, R.layout.quote_widget)
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(AppConfig.BASE_URL))
    val flags = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, flags)

    views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent)

    API.quoteService.getQuote().enqueue(object : Callback<Quote> {
        override fun onResponse(call: Call<Quote>, response: Response<Quote>) {
            if (response.isSuccessful && response.body() != null) {
                val quote = response.body()!!
                val text = buildSpannedString {
                    italic { append(quote.phrase) }
                    bold { append(" ~ ${quote.thinker}") }
                }
                views.setTextViewText(R.id.appwidget_text, text)
                views.setImageViewResource(R.id.appwidget_image, backgrounds.random())
                appWidgetManager.partiallyUpdateAppWidget(appWidgetId, views)
            }
        }

        override fun onFailure(call: Call<Quote>, t: Throwable) {}
    })
}

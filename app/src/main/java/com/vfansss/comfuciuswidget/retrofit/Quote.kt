package com.vfansss.comfuciuswidget.retrofit

import com.google.gson.annotations.SerializedName

data class Quote(
    @SerializedName("Phrase") val phrase: String,
    @SerializedName("Thinker") val thinker: String
)

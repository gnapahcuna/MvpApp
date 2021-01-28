package com.paiwaddev.kmids.mvpapp.data

import com.google.gson.annotations.SerializedName

data class WeatherResponse (
    @SerializedName("cod") val cod: Int,
    @SerializedName("message") val message: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("base") val base: String?,
    @SerializedName("main") val main: Main?,
    @SerializedName("weather") val weather: List<Weather>?,
    @SerializedName("clouds") val clouds: Clouds?,
    @SerializedName("wind") val wind: Wind?,
)

data class Weather (
    @SerializedName("id") var id: Int,
    @SerializedName("main") var main: String,
    @SerializedName("description") var description: String,
    @SerializedName("icon") var icon: String?
)

data class Clouds (
    @SerializedName("all") var all: Float
    )


data class Wind (
    @SerializedName("speed") var speed: Float,
    @SerializedName("deg") var deg: Float
    )

data class Main(
    @SerializedName("temp") val temp: Float,
    @SerializedName("feels_like") val feels_like: Float,
    @SerializedName("temp_min") val temp_min: Float,
    @SerializedName("temp_max") val temp_max: Float,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("humidity") val humidity: Int,
)

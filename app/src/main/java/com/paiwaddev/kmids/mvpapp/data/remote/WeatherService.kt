package com.paiwaddev.kmids.mvpapp.data.remote

import com.paiwaddev.kmids.mvpapp.data.WeatherResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("data/2.5/weather?")
    fun getWeather(@Query("q") q: String, @Query("appid") app_id: String): Observable<WeatherResponse>
}
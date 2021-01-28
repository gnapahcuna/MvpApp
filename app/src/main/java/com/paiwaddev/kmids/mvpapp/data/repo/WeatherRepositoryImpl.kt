package com.paiwaddev.kmids.mvpapp.data.repo

import com.paiwaddev.kmids.mvpapp.data.WeatherResponse
import com.paiwaddev.kmids.mvpapp.data.remote.WeatherService
import io.reactivex.rxjava3.core.Observable

class WeatherRepositoryImpl(private val weatherService: WeatherService,private val APPID: String): WeatherRepositoty {
    override fun getCurrentWeather(q: String): Observable<WeatherResponse> {
        return weatherService.getWeather(q,APPID).map { it }
    }


}
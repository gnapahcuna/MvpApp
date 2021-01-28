package com.paiwaddev.kmids.mvpapp.data.repo

import com.paiwaddev.kmids.mvpapp.data.WeatherResponse
import io.reactivex.rxjava3.core.Observable

interface WeatherRepositoty {

    fun getCurrentWeather(q: String): Observable<WeatherResponse>

}
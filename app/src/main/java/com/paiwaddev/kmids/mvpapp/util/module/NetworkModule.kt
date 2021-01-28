package com.paiwaddev.paiwadpos.utils.module

import com.paiwaddev.kmids.mvpapp.data.remote.WeatherService
import com.paiwaddev.paiwadpos.data.remote.OkHttpBuilder
import com.paiwaddev.paiwadpos.data.remote.RetrofitBuilder
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "http://api.openweathermap.org/"

val networkModule = module {
    single { OkHttpBuilder().build() }
    single<CallAdapter.Factory> { RxJava3CallAdapterFactory.create() }
    single<Converter.Factory> { GsonConverterFactory.create() }
    single { RetrofitBuilder(get(),get(),get()) }
    single<WeatherService> { get<RetrofitBuilder>().build(BASE_URL) }

}
package com.paiwaddev.paiwadpos.utils.module

import com.paiwaddev.kmids.mvpapp.data.repo.WeatherRepositoryImpl
import com.paiwaddev.kmids.mvpapp.data.repo.WeatherRepositoty
import com.paiwaddev.kmids.mvpapp.viewModel.PresentViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val APP_ID = "52c6d7b34a42dbdd0fecd1ca63f90a3e"

val weatherModule = module {

    factory<WeatherRepositoty> {WeatherRepositoryImpl(get(),APP_ID) }
    single  { PresentViewModel(get(),androidContext()) }

}
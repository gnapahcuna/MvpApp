package com.paiwaddev.kmids.mvpapp.viewModel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paiwaddev.kmids.mvpapp.data.WeatherResponse
import com.paiwaddev.kmids.mvpapp.data.repo.WeatherRepositoty
import com.paiwaddev.kmids.mvpapp.ui.present.PresentFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.DecimalFormat

class PresentViewModel(private val repository: WeatherRepositoty, private val context: Context) : ViewModel() {

    private var sharedPref = context.getSharedPreferences("Search",Context.MODE_PRIVATE)
    private var _weather = MutableLiveData<WeatherResponse >()
    private var search = MutableLiveData<String>()
    private var errorMsg = MutableLiveData<String>()
    private val compositeDisposable = CompositeDisposable()

    private var _temperator = MutableLiveData<Pair<String,String>>()

    fun errorMessage(): LiveData<String> = errorMsg

    fun weather(): LiveData<WeatherResponse> = _weather
    fun textSearch(): LiveData<String> = search

    fun initSearch() {
        val data = sharedPref.getString("q", null)
        search.value = data
        if (data != null)
            onGetWeather(data)
    }

    fun getCurrentWeather(q: String){
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString("q",q)
        editor.commit()

        onGetWeather(q)
    }

    fun onGetWeather(q: String){
        errorMsg.postValue(null)
        val disposable = repository.getCurrentWeather(q)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ item ->
                if (item.cod == 200) {
                    _weather.value = item
                } else {
                    errorMsg.value = item.message
                }
            }, { error ->
                errorMsg.value = error.localizedMessage
            })

        compositeDisposable.add(disposable)
    }


    fun convertTempTo(converTO: String){
        val dec  = DecimalFormat("#,###.##")
        var cel: Float? = null
        if(converTO == PresentFragment.CELSIUS){
            cel = _weather.value?.main!!.temp - 273.15.toFloat()
        }else if(converTO == PresentFragment.FAHRENHEIT){
            cel = _weather.value?.main!!.temp - (9/5+32)
        }
        cel?.let {
            val temp = Pair<String, String>(dec.format(cel),converTO)
            _temperator.value = temp
        }
    }

    var temperater: LiveData<Pair<String,String>> = _temperator

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}
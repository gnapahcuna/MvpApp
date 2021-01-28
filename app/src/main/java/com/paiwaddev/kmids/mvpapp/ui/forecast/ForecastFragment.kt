package com.paiwaddev.kmids.mvpapp.ui.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.paiwaddev.kmids.mvpapp.R
import com.paiwaddev.kmids.mvpapp.viewModel.PresentViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ForecastFragment : Fragment() {

    private val presentViewModel: PresentViewModel by sharedViewModel()

    private lateinit var tvMainWeather: TextView
    private lateinit var tvDescriptionWeather: TextView
    private lateinit var tvWind: TextView
    private lateinit var tvClouds: TextView
    private lateinit var tvWindUnit: TextView
    private lateinit var tvCloudUnit: TextView
    private lateinit var imgWeather: ImageView
    private lateinit var layoutMain:ConstraintLayout

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_forecast, container, false)

        onBildingView(root)

        presentViewModel.weather().observe(viewLifecycleOwner, {
            it.weather?.forEach { weather ->

                tvMainWeather.setText(weather.main)
                tvDescriptionWeather.setText(weather.description)

                Glide.with(this)
                        .load("http://openweathermap.org/img/wn/${weather.icon}@2x.png")
                        .centerCrop()
                        .into(imgWeather)
            }
            layoutMain.visibility = View.VISIBLE

            tvWind.text = it.wind?.speed.toString()
            tvWindUnit.text = resources.getString(R.string.text_wind_unit)
            tvCloudUnit.text = resources.getString(R.string.text_cloud_unit)
            tvClouds.text = it.clouds?.all.toString()
        })

        presentViewModel.errorMessage().observe(viewLifecycleOwner, {
            it?.let {
                layoutMain.visibility = View.GONE
            }

        })

        return root
    }

    fun onBildingView(v: View){
        tvMainWeather = v.findViewById(R.id.tv_weather_main)
        tvDescriptionWeather = v.findViewById(R.id.tv_weather_description)
        tvWind = v.findViewById(R.id.tv_wind)
        tvClouds = v.findViewById(R.id.tv_clouds)
        tvWindUnit = v.findViewById(R.id.tv_wind_unit)
        tvCloudUnit = v.findViewById(R.id.tv_clouds_unit)
        imgWeather = v.findViewById(R.id.imageWeather)
        layoutMain = v.findViewById(R.id.layout_forecast)
        layoutMain.visibility = View.GONE
    }
}
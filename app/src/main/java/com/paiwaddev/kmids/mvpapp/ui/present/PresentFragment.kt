package com.paiwaddev.kmids.mvpapp.ui.present

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.paiwaddev.kmids.mvpapp.R
import com.paiwaddev.kmids.mvpapp.viewModel.PresentViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class PresentFragment : Fragment(), View.OnClickListener, TabLayout.OnTabSelectedListener {

    private val presentViewModel: PresentViewModel by sharedViewModel()
    private lateinit var editText: EditText
    private lateinit var tvTemp: TextView
    private lateinit var tvHumid: TextView
    private lateinit var tvLocationName: TextView
    private lateinit var tvTempUnit: TextView
    private lateinit var tvHumidUnit: TextView
    private lateinit var btnSearch: Button
    private lateinit var tabConvert: TabLayout
    private lateinit var layoutOutput: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_present, container, false)

        onBildingView(root)

        tabConvert.addOnTabSelectedListener(this)

        btnSearch.setOnClickListener(this)

        onSubscribeInitSearch()

        onSubscibeWeather()

        onSubscribeSwitchTemp()


        //error
        presentViewModel.errorMessage().observe(viewLifecycleOwner, {
            it?.let {
                tvTemp.text = ""
                tvHumid.text = ""
                tvLocationName.text = ""
                layoutOutput.visibility = View.GONE
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })

        return root
    }

    fun onBildingView(v: View) {
        editText = v.findViewById(R.id.edt_place)
        tvTemp = v.findViewById(R.id.tv_temp)
        tvHumid = v.findViewById(R.id.tv_humid)
        tvLocationName = v.findViewById(R.id.tvNameLocation)
        tvTempUnit = v.findViewById(R.id.tv_temp_unit)
        tvHumidUnit = v.findViewById(R.id.tv_humid_unit)
        tabConvert = v.findViewById(R.id.tabConvert)
        btnSearch = v.findViewById(R.id.buttonSearch)
        layoutOutput = v.findViewById(R.id.layout_output)
        layoutOutput.visibility = View.GONE
    }

    fun onSubscibeWeather(){
        //success
        presentViewModel.weather().observe(viewLifecycleOwner, {
            layoutOutput.visibility = View.VISIBLE
            presentViewModel.convertTempTo(CELSIUS)
            tvHumid.text = it.main?.humidity.toString()
            tvHumidUnit.text = getString(R.string.text_humid_unit)
            tvLocationName.text = it.name.toString()
        })
    }

    fun onSubscribeInitSearch(){
        presentViewModel.initSearch()
        presentViewModel.textSearch().observe(viewLifecycleOwner, {
            it?.let {
                editText.setText(it)
            }
        })
    }

    fun onSubscribeSwitchTemp(){
        presentViewModel.temperater.observe(viewLifecycleOwner, {
            tvTemp.text = it.first
            tvTempUnit.text = it.second
        })
    }

    override fun onClick(v: View?) {
        presentViewModel.getCurrentWeather(editText.text.toString())
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        when (tab!!.position) {
            0 -> {
                presentViewModel.convertTempTo(CELSIUS)
            }
            1 -> {
                presentViewModel.convertTempTo(FAHRENHEIT)
            }

        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    companion object {
        var CELSIUS: String = "C"
        var FAHRENHEIT: String = "F"
    }
}
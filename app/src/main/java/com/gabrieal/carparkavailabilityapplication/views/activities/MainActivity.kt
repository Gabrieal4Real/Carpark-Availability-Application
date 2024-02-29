package com.gabrieal.carparkavailabilityapplication.views.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabrieal.carparkavailabilityapplication.R
import com.gabrieal.carparkavailabilityapplication.databinding.ActivityMainBinding
import com.gabrieal.carparkavailabilityapplication.models.CategorySizeState
import com.gabrieal.carparkavailabilityapplication.models.carpark.CarParkDataItemModel
import com.gabrieal.carparkavailabilityapplication.models.dataModels.CarParkCategoryItemModel
import com.gabrieal.carparkavailabilityapplication.models.dataModels.CarParkCategoryModel
import com.gabrieal.carparkavailabilityapplication.utils.Constants
import com.gabrieal.carparkavailabilityapplication.viewModels.carPark.CarParkViewModelImpl
import com.gabrieal.carparkavailabilityapplication.views.adapters.CarParkListAdapter
import com.gabrieal.carparkavailabilityapplication.views.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : BaseActivity() {
    private var binding: ActivityMainBinding? = null
    private val carParkViewModel: CarParkViewModelImpl by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onSetupViewModel()
        onBindData()
        setupUI()
        observeResponses()
    }

    private fun onSetupViewModel() {

    }

    private fun onBindData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding?.lifecycleOwner = this

        callViewModel()
    }

    private fun loopViewModelCall() {
        Handler(Looper.getMainLooper()).postDelayed(::callViewModel, 60000)
    }

    private fun callViewModel() {
        carParkViewModel.getCarParkAvailability(
            SimpleDateFormat(
                Constants.CONST_yyyymmddhhmmss_FORMAT, Locale.getDefault()
            ).format(Date())
        )
        loopViewModelCall()
    }

    private fun setupUI() {

    }

    private fun setupAdapter(results: List<CarParkDataItemModel>?) {
        binding?.rvCarParkList?.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<CarParkCategoryModel>()
        val dataItems = ArrayList<CarParkCategoryItemModel>()
        if (!results.isNullOrEmpty()) {
            results.forEach { result ->
                var totalLots = 0
                var lotsAvailable = 0
                result.carpark_info?.forEach {
                    //in the event BE sends inconvertible strings, I wrapped in try catch
                    try {
                        totalLots += it.total_lots?.toInt()!!
                        lotsAvailable += it.lots_available?.toInt()!!
                    } catch (_: Exception) {
                    }
                }
                dataItems.add(
                    CarParkCategoryItemModel(totalLots, result.carpark_number, lotsAvailable)
                )
            }
        }

        dataItems.sortBy { it.lotsAvailable }

        CategorySizeState.values().forEach { categorySizeState ->
            val carParkDataItem = ArrayList<CarParkCategoryItemModel>()
            carParkDataItem.addAll(dataItems.filter {
                (it.totalLots!! >= CategorySizeState.getMinMaxLimit(categorySizeState.name).first
                        && it.totalLots!! < CategorySizeState.getMinMaxLimit(categorySizeState.name).second)
            })
            data.add(CarParkCategoryModel(categorySizeState.name, carParkDataItem))
        }

        val adapter = CarParkListAdapter(data)
        binding?.rvCarParkList?.adapter = adapter
    }

    private fun observeResponses() {
        carParkViewModel.observeCarParkAvailability().observe(this) {
            it?.let {
                setupAdapter(it)
            }
        }

        carParkViewModel.observeTimeStamp().observe(this) {
            it?.let {
                binding?.tvRefreshedAt?.text = it
            }
        }
    }
}
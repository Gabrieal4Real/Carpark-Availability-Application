package com.gabrieal.carparkavailabilityapplication.views.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabrieal.carparkavailabilityapplication.R
import com.gabrieal.carparkavailabilityapplication.databinding.ActivityMainBinding
import com.gabrieal.carparkavailabilityapplication.models.CategorySizeState
import com.gabrieal.carparkavailabilityapplication.models.carpark.CarParkDataItemModel
import com.gabrieal.carparkavailabilityapplication.models.dataModels.CarParkCategoryItemModel
import com.gabrieal.carparkavailabilityapplication.models.dataModels.CarParkCategoryModel
import com.gabrieal.carparkavailabilityapplication.viewModels.carPark.CarParkViewModel
import com.gabrieal.carparkavailabilityapplication.viewModels.carPark.CarParkViewModelImpl
import com.gabrieal.carparkavailabilityapplication.views.adapters.CarParkListAdapter
import com.gabrieal.carparkavailabilityapplication.views.base.BaseActivity
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var binding: ActivityMainBinding? = null
    private lateinit var viewModel: CarParkViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onSetupViewModel()
        onBindData()
        setupUI()
        observeResponses()
    }

    private fun onSetupViewModel() {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory)[CarParkViewModelImpl::class.java]
    }

    private fun onBindData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = this

        callViewModel()
    }

    private fun loopViewModelCall() {
        Handler(Looper.getMainLooper()).postDelayed(::callViewModel, 60000)
    }

    private fun callViewModel() {
        viewModel.getCarParkAvailability(
            SimpleDateFormat(
                "yyyy-MM-dd'T'hh:mm:ss", Locale.getDefault()
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
            for (result in results) {
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
                    CarParkCategoryItemModel(
                        totalLots, result.carpark_number, lotsAvailable
                    )
                )
            }
        }

        dataItems.sortBy { it.totalLots }

        CategorySizeState.values().forEach { categorySizeState ->
            val carParkDataItem = ArrayList<CarParkCategoryItemModel>()
            carParkDataItem.addAll(dataItems.filter {
                (it.totalLots!! >= CategorySizeState.getMinMaxLimit(
                    categorySizeState.name
                ).first && it.totalLots!! < CategorySizeState.getMinMaxLimit(categorySizeState.name).second)
            })
            data.add(CarParkCategoryModel(categorySizeState.name, carParkDataItem))
        }

        val adapter = CarParkListAdapter(data)
        binding?.rvCarParkList?.adapter = adapter
    }

    private fun observeResponses() {
        viewModel.observeCarParkAvailability().observe(this) {
            it?.let {
                setupAdapter(it.items?.get(0)?.carpark_data)
            }
        }

        viewModel.observeTimeStamp().observe(this) {
            it?.let {
                binding?.tvRefreshedAt?.text =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US).parse(it)
                        ?.toLocaleString()
            }
        }
    }
}
package com.gabrieal.carparkavailabilityapplication.views.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.gabrieal.carparkavailabilityapplication.R
import com.gabrieal.carparkavailabilityapplication.databinding.ActivityMainBinding
import com.gabrieal.carparkavailabilityapplication.viewModels.carPark.CarParkViewModel
import com.gabrieal.carparkavailabilityapplication.viewModels.carPark.CarParkViewModelImpl
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

    private fun callViewModel () {
        viewModel.getCarParkAvailability(SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.getDefault()).format(Date()))
        loopViewModelCall()
    }

    private fun setupUI() {
    }

    private fun observeResponses() {
        viewModel.observeCarParkAvailability().observe(this) {
            it?.let {
                Log.d("QPOEJGPOQWEJKG", "QPEOJGPQWEjgpwe")
            }
        }
    }
}
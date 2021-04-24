package io.arunbuilds.runkeeper.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import io.arunbuilds.runkeeper.R
import io.arunbuilds.runkeeper.other.TrackingUtility
import io.arunbuilds.runkeeper.ui.videmodels.StatisticsViewModel
import kotlinx.android.synthetic.main.fragment_statistics.*
import kotlin.math.round

@AndroidEntryPoint
class StatisticsFragment : Fragment(R.layout.fragment_statistics) {
    private val viewModel: StatisticsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribetoObservers()
    }

    private fun subscribetoObservers() {
        viewModel.totalTimeRun.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalRunTime = TrackingUtility.getFormattedStopWatchTime(it)
                tvTotalTime.text = totalRunTime
            }
        })

        viewModel.totalCaloriesBurned.observe(viewLifecycleOwner, Observer {
            it?.let {
                val calsBurned = "$it kcal"
                tvTotalCalories.text = calsBurned

            }
        })

        viewModel.totalDistance.observe(viewLifecycleOwner, Observer {
            it?.let {
                val km = it / 1000
                val totalDistance = round(km * 10f) / 10f
                tvTotalDistance.text = "$totalDistance Km"
            }
        })

        viewModel.avgSpeed.observe(viewLifecycleOwner, Observer {
            it?.let {
                val km = it / 1000
                val avgSpeed = round(km * 10f) / 10f
                tvAverageSpeed.text = "$avgSpeed Km/h"
            }
        })
    }
}
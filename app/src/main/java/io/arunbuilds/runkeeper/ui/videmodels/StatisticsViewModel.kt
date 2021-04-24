package io.arunbuilds.runkeeper.ui.videmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.arunbuilds.runkeeper.repositories.MainRepository
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    val totalTimeRun = mainRepository.getTotalRunTimeInMillis()
    val totalDistance = mainRepository.getTotalDistance()
    val totalCaloriesBurned = mainRepository.getTotalCaloriesBurnt()
    val avgSpeed = mainRepository.getAvgSpeed()

    val runsSortedByDate = mainRepository.getAllRunsSortedByDate()

}
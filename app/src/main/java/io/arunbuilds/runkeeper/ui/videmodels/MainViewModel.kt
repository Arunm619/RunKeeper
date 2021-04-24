package io.arunbuilds.runkeeper.ui.videmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.arunbuilds.runkeeper.db.Run
import io.arunbuilds.runkeeper.other.SortType
import io.arunbuilds.runkeeper.other.SortType.*
import io.arunbuilds.runkeeper.repositories.MainRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    private val runsSortedByDate = mainRepository.getAllRunsSortedByDate()
    private val runsSortedByDistance = mainRepository.getAllRunsSortedByDistance()
    private val runsSortedByCaloriesBurned = mainRepository.getAllRunsSortedByCaloriesBurnt()
    private val runsSortedbyRunTime = mainRepository.getAllRunsSortedByRuntime()
    private val runsSortedByAvgSpeed = mainRepository.getAllRunsSortedByAverageSpeed()


    val runs = MediatorLiveData<List<Run>>()
    var sortType = DATE

    init {
        runs.addSource(runsSortedByDate) { result ->
            if (sortType == DATE) {
                result?.let {
                    runs.value = it
                }
            }
        }

        runs.addSource(runsSortedByAvgSpeed) { result ->
            if (sortType == AVG_SPEED) {
                result?.let {
                    runs.value = it
                }
            }
        }

        runs.addSource(runsSortedByCaloriesBurned) { result ->
            if (sortType == CALORIES_BURNT) {
                result?.let {
                    runs.value = it
                }
            }
        }

        runs.addSource(runsSortedByDistance) { result ->
            if (sortType == DISTANCE) {
                result?.let {
                    runs.value = it
                }
            }
        }

        runs.addSource(runsSortedbyRunTime) { result ->
            if (sortType == RUNNING_TIME) {
                result?.let {
                    runs.value = it
                }
            }
        }

    }

    fun insertRun(run: Run) = viewModelScope.launch {
        mainRepository.insertRun(run)
    }

    fun sortRuns(sortType: SortType) = when (sortType) {
        DATE -> runsSortedByDate.value?.let { runs.value = it }
        RUNNING_TIME -> runsSortedbyRunTime.value?.let { runs.value = it }
        AVG_SPEED -> runsSortedByAvgSpeed.value?.let { runs.value = it }
        DISTANCE -> runsSortedByDistance.value?.let { runs.value = it }
        CALORIES_BURNT -> runsSortedByCaloriesBurned.value?.let { runs.value = it }
    }.also { this.sortType = sortType }
}
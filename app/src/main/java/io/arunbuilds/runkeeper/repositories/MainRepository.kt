package io.arunbuilds.runkeeper.repositories

import io.arunbuilds.runkeeper.db.Run
import io.arunbuilds.runkeeper.db.RunDAO
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val runDAO: RunDAO
) {
    suspend fun insertRun(run: Run) = runDAO.insertRun(run)

    suspend fun deleteRun(run: Run) = runDAO.deleteRun(run)

    fun getAllRunsSortedByDate() = runDAO.getAllRunsSortedByDate()

    fun getAllRunsSortedByAverageSpeed() = runDAO.getAllRunsSortedByAverageSpeed()

    fun getAllRunsSortedByDistance() = runDAO.getAllRunsSortedByDistance()

    fun getAllRunsSortedByRuntime() = runDAO.getAllRunsSortedByRuntime()

    fun getAllRunsSortedByCaloriesBurnt() = runDAO.getAllRunsSortedByCaloriesBurnt()

    fun getTotalCaloriesBurnt() = runDAO.getTotalCaloriesBurnt()

    fun getTotalDistance() = runDAO.getTotalDistance()

    fun getTotalRunTimeInMillis() = runDAO.getTotalRunTimeInMillis()

    fun getAvgSpeed() = runDAO.getAverageSpeedinKMPH()

}
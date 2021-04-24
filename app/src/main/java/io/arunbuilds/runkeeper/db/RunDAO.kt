package io.arunbuilds.runkeeper.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RunDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRun(run: Run)

    @Delete
    suspend fun deleteRun(run: Run)

    @Query("select * from running_table order by timeStamp DESC")
    fun getAllRunsSortedByDate(): LiveData<List<Run>>


    @Query("select * from running_table order by lengthOfRunTimeInMillis DESC")
    fun getAllRunsSortedByRuntime(): LiveData<List<Run>>


    @Query("select * from running_table order by caloriesBurnt DESC")
    fun getAllRunsSortedByCaloriesBurnt(): LiveData<List<Run>>


    @Query("select * from running_table order by averageSpeedInKMPH DESC")
    fun getAllRunsSortedByAverageSpeed(): LiveData<List<Run>>

    @Query("select * from running_table order by distanceInMeters DESC")
    fun getAllRunsSortedByDistance(): LiveData<List<Run>>

    @Query("select SUM(lengthOfRunTimeInMillis) from running_table")
    fun getTotalRunTimeInMillis(): LiveData<Long>

    @Query("select SUM(caloriesBurnt) from running_table")
    fun getTotalCaloriesBurnt(): LiveData<Int>

    @Query("select SUM(distanceInMeters) from running_table")
    fun getTotalDistance(): LiveData<Int>

    @Query("select AVG(averageSpeedInKMPH) from running_table")
    fun getAverageSpeedinKMPH(): LiveData<Float>
}
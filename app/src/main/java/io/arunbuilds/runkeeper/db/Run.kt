package io.arunbuilds.runkeeper.db

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "running_table")
data class Run(
    val previewImage: Bitmap? = null,
    val timeStamp: Long = 0L, // when the run happened (date converted to millis)
    val averageSpeedInKMPH: Float = 0f,
    val distanceInMeters: Int = 0,
    val lengthOfRunTimeInMillis: Long = 0L, // length of run
    val caloriesBurnt: Int = 0
) {
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
}
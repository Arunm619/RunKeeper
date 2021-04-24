package io.arunbuilds.runkeeper.db

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "running_table")
data class Run(
    var previewImage: Bitmap? = null,
    var timeStamp: Long = 0L, // when the run happened (date converted to millis)
    var averageSpeedInKMPH: Float = 0f,
    var distanceInMeters: Int = 0,
    var lengthOfRunTimeInMillis: Long = 0L, // length of run
    var caloriesBurnt: Int = 0
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
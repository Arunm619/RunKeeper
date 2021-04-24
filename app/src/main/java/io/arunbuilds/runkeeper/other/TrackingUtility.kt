package io.arunbuilds.runkeeper.other

import android.Manifest
import android.content.Context
import android.location.Location
import android.os.Build
import io.arunbuilds.runkeeper.services.Polyline
import pub.devrel.easypermissions.EasyPermissions
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.concurrent.TimeUnit

object TrackingUtility {

    val ANDROID_Q_PERMS = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION
    )

    val LOCATION_PERMS = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
    )

    fun hasLocationPermissions(context: Context) =
        if (isLessThanQ()) {
            EasyPermissions.hasPermissions(
                context,
                *LOCATION_PERMS.toTypedArray()
            )
        } else {
            EasyPermissions.hasPermissions(
                context,
                *ANDROID_Q_PERMS.toTypedArray()
            )
        }

    fun isLessThanQ() = Build.VERSION.SDK_INT < Build.VERSION_CODES.Q

    fun atleastOreo(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
    }

    fun getFormattedStopWatchTime(ms: Long, includeMillis: Boolean = false): String {
        var milliseconds = ms
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        milliseconds -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)
        if (!includeMillis) {
            val f: NumberFormat = DecimalFormat("00")
            return "${f.format(hours)}:${f.format(minutes)}:${f.format(seconds)}"
        }
        milliseconds -= TimeUnit.SECONDS.toMillis(seconds)
        milliseconds /= 10

        val f: NumberFormat = DecimalFormat("00")
        return "${f.format(hours)}:${f.format(minutes)}:${f.format(seconds)}:${f.format(milliseconds)}"
    }

    fun calculatePolylineLength(polyline: Polyline): Float {
        var distance = 0f
        for (i in 0..polyline.size - 2) {
            val pos1 = polyline[i]
            val pos2 = polyline[i+1]
            val result = FloatArray(1)
            Location.distanceBetween(pos1.latitude,pos1.longitude,pos2.latitude,pos2.longitude, result)
            distance += result[0]
        }
        return distance
    }
}
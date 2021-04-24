package io.arunbuilds.runkeeper.other

import android.Manifest
import android.content.Context
import android.os.Build
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
        if(!includeMillis){
            val f: NumberFormat = DecimalFormat("00")
            return "${f.format(hours)}:${f.format(minutes)}:${f.format(seconds)}"
        }
        milliseconds -= TimeUnit.SECONDS.toMillis(seconds)
        milliseconds /= 10

        val f: NumberFormat = DecimalFormat("00")
        return "${f.format(hours)}:${f.format(minutes)}:${f.format(seconds)}:${f.format(milliseconds)}"
    }
}
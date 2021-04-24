package io.arunbuilds.runkeeper.other

import android.Manifest
import android.content.Context
import android.os.Build
import pub.devrel.easypermissions.EasyPermissions

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
}
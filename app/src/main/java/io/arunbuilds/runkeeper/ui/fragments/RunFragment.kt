package io.arunbuilds.runkeeper.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.arunbuilds.runkeeper.R
import io.arunbuilds.runkeeper.other.Constants.REQUEST_CODE_LOCATION_PERMISSION
import io.arunbuilds.runkeeper.other.TrackingUtility
import io.arunbuilds.runkeeper.other.TrackingUtility.ANDROID_Q_PERMS
import io.arunbuilds.runkeeper.other.TrackingUtility.LOCATION_PERMS
import io.arunbuilds.runkeeper.ui.videmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_run.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber

@AndroidEntryPoint
class RunFragment : Fragment(R.layout.fragment_run), EasyPermissions.PermissionCallbacks {
    private val viewModel: MainViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
        }
        requestPermission()
    }

    private fun requestPermission() {
        if (TrackingUtility.hasLocationPermissions(requireContext())) {
            return
        }
        if (TrackingUtility.isLessThanQ()) {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.perms_rationale),
                REQUEST_CODE_LOCATION_PERMISSION,
                *LOCATION_PERMS.toTypedArray()
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.perms_rationale),
                REQUEST_CODE_LOCATION_PERMISSION,
                *ANDROID_Q_PERMS.toTypedArray()
            )
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Timber.d("Permissions granted")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}
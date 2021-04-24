package io.arunbuilds.runkeeper.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.arunbuilds.runkeeper.R
import io.arunbuilds.runkeeper.adapters.RunAdapter
import io.arunbuilds.runkeeper.other.Constants
import io.arunbuilds.runkeeper.other.Constants.REQUEST_CODE_LOCATION_PERMISSION
import io.arunbuilds.runkeeper.other.SortType
import io.arunbuilds.runkeeper.other.TrackingUtility
import io.arunbuilds.runkeeper.other.TrackingUtility.ANDROID_Q_PERMS
import io.arunbuilds.runkeeper.other.TrackingUtility.LOCATION_PERMS
import io.arunbuilds.runkeeper.ui.videmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_run.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class RunFragment : Fragment(R.layout.fragment_run), EasyPermissions.PermissionCallbacks {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var runAdapter: RunAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()


        when (viewModel.sortType) {
            SortType.DATE -> spFilter.setSelection(0)
            SortType.RUNNING_TIME -> spFilter.setSelection(1)
            SortType.DISTANCE -> spFilter.setSelection(2)
            SortType.AVG_SPEED -> spFilter.setSelection(3)
            SortType.CALORIES_BURNT -> spFilter.setSelection(4)

        }

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
        }

        spFilter.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
              when(position){
                  0 ->  viewModel.sortRuns(SortType.DATE)
                  1 ->  viewModel.sortRuns(SortType.RUNNING_TIME)
                  2 ->  viewModel.sortRuns(SortType.DISTANCE)
                  3 ->  viewModel.sortRuns(SortType.AVG_SPEED)
                  4 ->  viewModel.sortRuns(SortType.CALORIES_BURNT)

              }
            }

        }

        requestPermission()

        viewModel.runs.observe(viewLifecycleOwner, Observer {
            runAdapter.submitList(it)
        })
    }

    private fun setupRecyclerView() = rvRuns.apply {
        runAdapter = RunAdapter()
        adapter = runAdapter
        layoutManager = LinearLayoutManager(requireContext())
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
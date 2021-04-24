package io.arunbuilds.runkeeper.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import dagger.hilt.android.AndroidEntryPoint
import io.arunbuilds.runkeeper.R
import io.arunbuilds.runkeeper.other.Constants
import io.arunbuilds.runkeeper.other.Constants.ACTION_PAUSE_SERVICE
import io.arunbuilds.runkeeper.other.Constants.ACTION_START_OR_RESUME_SERVICE
import io.arunbuilds.runkeeper.other.Constants.MAP_ZOOM
import io.arunbuilds.runkeeper.other.Constants.POLYLINE_COLOR
import io.arunbuilds.runkeeper.other.Constants.POLYLINE_WIDTH
import io.arunbuilds.runkeeper.other.TrackingUtility
import io.arunbuilds.runkeeper.services.Polyline
import io.arunbuilds.runkeeper.services.TrackingService
import io.arunbuilds.runkeeper.ui.videmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_tracking.*

@AndroidEntryPoint
class TrackingFragment : Fragment(R.layout.fragment_tracking) {
    private val viewModel: MainViewModel by viewModels()
    private var map: GoogleMap? = null

    private var currTimeInMillis = 0L

    private var isTracking = false
    private var pathPoints = mutableListOf<Polyline>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView?.onCreate(savedInstanceState)

        mapView?.getMapAsync {
            map = it
            addAllPolyLines()
        }

        btnToggleRun.setOnClickListener {
            toggleRun()
        }

        subscribeToObservers()
    }

    private fun addLatestPolyLine() {
        if (pathPoints.isNotEmpty() && pathPoints.last().size > 1) {
            val preLastLatLong: LatLng = pathPoints.last()[pathPoints.last().size - 2]
            val lastLatLong: LatLng = pathPoints.last().last()

            val polyLineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .add(preLastLatLong)
                .add(lastLatLong)
            map?.addPolyline(polyLineOptions)
        }
    }

    private fun addAllPolyLines() {
        for (polyLine in pathPoints) {
            val polyLineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .addAll(polyLine)
            map?.addPolyline(polyLineOptions)
        }
    }


    private fun moveCameratoUser() {
        if (pathPoints.isNotEmpty() && pathPoints.last().isNotEmpty()) {
            map?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    pathPoints.last().last(),
                    MAP_ZOOM
                )
            )
        }
    }

    private fun updateTracking(isTracking: Boolean) {
        this.isTracking = isTracking
        if (!isTracking) {
            btnToggleRun.text = "START"
            btnFinishRun.visibility = View.VISIBLE
        } else {
            btnToggleRun.text = "STOP"
            btnFinishRun.visibility = View.GONE
        }
    }

    private fun toggleRun() {
        if (isTracking) {
            sendCommandtoService(ACTION_PAUSE_SERVICE)
        } else {
            sendCommandtoService(ACTION_START_OR_RESUME_SERVICE)
        }
    }

    private fun subscribeToObservers() {
        TrackingService.isTracking.observe(viewLifecycleOwner, Observer {
            updateTracking(it)
        })
        TrackingService.pathPoints.observe(viewLifecycleOwner, Observer {
            pathPoints = it
            addLatestPolyLine()
            moveCameratoUser()
        })

        TrackingService.timeRunInMillis.observe(viewLifecycleOwner, Observer {
            currTimeInMillis = it
            val formattedTime = TrackingUtility.getFormattedStopWatchTime(currTimeInMillis, true)
            tvTimer.text = formattedTime

        })
    }

    private fun sendCommandtoService(action: String) =
        Intent(requireContext(), TrackingService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }
}
package io.arunbuilds.runkeeper.services

import android.content.Intent
import androidx.lifecycle.LifecycleService
import io.arunbuilds.runkeeper.other.Constants.ACTION_PAUSE_SERVICE
import io.arunbuilds.runkeeper.other.Constants.ACTION_START_OR_RESUME_SERVICE
import io.arunbuilds.runkeeper.other.Constants.ACTION_STOP_SERVICE
import timber.log.Timber

class TrackingService : LifecycleService() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    Timber.d("Started or Resumed Service")
                }
                ACTION_PAUSE_SERVICE -> {
                    Timber.d("Paused Service")
                }
                ACTION_STOP_SERVICE -> {
                    Timber.d("Stopped Service")
                }

            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

}
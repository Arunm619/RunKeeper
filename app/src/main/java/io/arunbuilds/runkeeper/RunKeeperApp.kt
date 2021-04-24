package io.arunbuilds.runkeeper

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import io.arunbuilds.runkeeper.db.RunDAO
import javax.inject.Inject

@HiltAndroidApp
class RunKeeperApp : Application(){
    @Inject
    lateinit var runDao: RunDAO

    override fun onCreate() {
        super.onCreate()
        Log.d("ARUN","Arun - ${runDao.hashCode()}")
    }
}
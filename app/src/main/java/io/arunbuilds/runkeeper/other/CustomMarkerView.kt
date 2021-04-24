package io.arunbuilds.runkeeper.other

import android.content.Context
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import io.arunbuilds.runkeeper.db.Run
import kotlinx.android.synthetic.main.marker_view.view.*
import java.text.SimpleDateFormat
import java.util.*

class CustomMarkerView(
    val runs: List<Run>,
    context: Context,
    layoutId: Int
) : MarkerView(context, layoutId) {
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)

        if (e == null)
            return
        val currentRunId = e.x.toInt()
        val run = runs[currentRunId]
        //time stamp
        val calendar = Calendar.getInstance().apply { timeInMillis = run.timeStamp }
        val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        tvDate.text = dateFormat.format(calendar.time)

        //average speed
        val avgSpeed = "${run.averageSpeedInKMPH} km/h"
        tvDate.text = avgSpeed

        //distance in km
        val distanceInKm = "${run.distanceInMeters / 1000f} km"
        tvDistance.text = distanceInKm

        //time run
        tvDuration.text = TrackingUtility.getFormattedStopWatchTime(run.lengthOfRunTimeInMillis)

        //calories burnt
        val caloriesBurned = "${run.caloriesBurnt} kcal"
        tvCaloriesBurned.text = caloriesBurned
    }

    override fun getOffset(): MPPointF {
        //numbers from Doc
        return MPPointF(-width / 2f, -height.toFloat())
    }
}
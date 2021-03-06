package io.arunbuilds.runkeeper.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.arunbuilds.runkeeper.R
import io.arunbuilds.runkeeper.db.Run
import io.arunbuilds.runkeeper.other.TrackingUtility
import kotlinx.android.synthetic.main.item_run.view.*
import java.text.SimpleDateFormat
import java.util.*

class RunAdapter : RecyclerView.Adapter<RunAdapter.RunViewHolder>() {
    inner class RunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    val diffCallBack = object : DiffUtil.ItemCallback<Run>() {
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }
    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitList(list: List<Run>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        return RunViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_run,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run = differ.currentList[position]
        holder.itemView.apply {
            //preview image
            Glide.with(this).load(run.previewImage).into(ivRunImage)


            //time stamp
            val calendar = Calendar.getInstance().apply { timeInMillis = run.timeStamp }
            val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
            tvDate.text = dateFormat.format(calendar.time)

            //average speed
            val avgSpeed = "${run.averageSpeedInKMPH} km/h"
            tvAvgSpeed.text = avgSpeed

            //distance in km
            val distanceInKm = "${run.distanceInMeters / 1000f} km"
            tvDistance.text = distanceInKm

            //time run
            tvTime.text = TrackingUtility.getFormattedStopWatchTime(run.lengthOfRunTimeInMillis)

            //calories burnt
            val caloriesBurned = "${run.caloriesBurnt} kcal"
            tvCalories.text = caloriesBurned


        }
    }
}
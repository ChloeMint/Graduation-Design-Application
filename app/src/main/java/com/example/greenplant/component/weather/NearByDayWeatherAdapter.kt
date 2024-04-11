package com.example.greenplant.component.weather

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.greenplant.databinding.NoticeItemBinding
import com.example.greenplant.entities.SkyCon
import com.example.greenplant.entities.Temperature
import com.example.greenplant.entities.getSky
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.roundToInt


class NearByDayWeatherAdapter(private val temperatureList: List<Temperature>,private val weatherList:List<SkyCon>) : RecyclerView.Adapter<NearByDayWeatherAdapter.ViewHolder>() {
    inner class ViewHolder(val binding:NoticeItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            NoticeItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        )
    }

    override fun getItemCount(): Int {
        return temperatureList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val temperature = temperatureList[position]
            val skyCon = weatherList[position]

            val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
            val zonedDateTime = ZonedDateTime.parse(temperature.date, formatter)
            val localDate = zonedDateTime.toLocalDate()
            val simpleDateString = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            date.text = simpleDateString


            val icon = getSky(skyCon.value)
            weatherIcon.setImageResource(icon.icon)
            weather.text = icon.info
            minTemperature.text = temperature.min.roundToInt().toString()
            maxTemperature.text = temperature.max.roundToInt().toString()
        }
    }
}
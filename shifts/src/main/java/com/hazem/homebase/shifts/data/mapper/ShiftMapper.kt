package com.hazem.homebase.shifts.data.mapper

import com.hazem.homebase.shifts.models.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

internal class ShiftMapper : Mapper<Shift, ShiftViewModel> {
    private val fromDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss XXX", Locale.getDefault())
    private val toDateFormat =
        SimpleDateFormat("EEE, MMMM d", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("GMT-08:00")
        }

    override fun from(input: Shift): ShiftViewModel {
        val title = "${input.name}'s Shift"
        val startDateConverted = convertDate(input.startDate)
        val endDateConverted = convertDate(input.endData)
        val startHour = getHour(input.startDate)
        val endHour = getHour(input.endData)
        val color = try {
            Color.valueOf(input.color.uppercase())
        } catch (e: Exception) {
            Color.TRANSPARENT
        }
        return ShiftViewModel(
            title = title,
            subtitle = input.role,
            shiftStartDate = startDateConverted,
            shiftEndDate = endDateConverted,
            shiftEndTime = endHour,
            shiftStartTime = startHour,
            color = color
        )
    }

    private fun convertDate(date: String): String {
        return try {
            val d = fromDateFormat.parse(date)
            toDateFormat.format(d)
        } catch (e: ParseException) {
            "N/A"
        }
    }

    private fun getHour(date: String): ShiftTime {
        try {
            val d = fromDateFormat.parse(date)
            val zone = date.split(" ").last()
            val calendar = Calendar.getInstance().apply {
                time = d
                timeZone = TimeZone.getTimeZone("GMT$zone")
            }
            val hour = calendar.get(Calendar.HOUR_OF_DAY).toString()
            val ampm =
                if (calendar.get(Calendar.AM_PM) == Calendar.AM) DayPeriod.AM.toString() else DayPeriod.PM.toString()
            return ShiftTime(hour, ampm)
        } catch (e: Exception) {
            return ShiftTime("NA", "NA")
        }

    }
}
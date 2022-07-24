package com.hazem.homebase.shifts.data.mapper

import com.hazem.homebase.shifts.models.Color
import com.hazem.homebase.shifts.models.Shift
import com.hazem.homebase.shifts.models.ShiftViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

internal class ShiftMapper : Mapper<Shift, ShiftViewModel> {
    private val fromDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
    private val toDateFormat = SimpleDateFormat("EEE, MMMM d aaa", Locale.getDefault())
    override fun from(input: Shift): ShiftViewModel {
        val title = "${input.name}'s Shift"
        val startDateConverted = convertDate(input.startDate)
        val endDateConverted = convertDate(input.endData)
        println(startDateConverted)
        println(endDateConverted)
        val color = try {
            Color.valueOf(input.color.uppercase())
        } catch (e: Exception) {
            Color.TRANSPARENT
        }
        return ShiftViewModel(
            title = title,
            subtitle = input.role,
            shiftStart = startDateConverted,
            shiftEnd = endDateConverted,
            color
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
}
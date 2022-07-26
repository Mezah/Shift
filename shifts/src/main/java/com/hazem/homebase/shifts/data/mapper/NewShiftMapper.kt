package com.hazem.homebase.shifts.data.mapper

import com.hazem.homebase.shifts.models.NewShift
import com.hazem.homebase.shifts.models.Shift
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class NewShiftMapper : Mapper<NewShift, Shift> {
    private val toStartDateFormat = SimpleDateFormat("yyyy-MM-dd H:mm:ss XXX", Locale.getDefault())
        .apply {
            timeZone = TimeZone.getTimeZone("GMT-08:00")
        }

    private val toEndDateFormat = SimpleDateFormat("yyyy-M-dd H:mm:ss XXX", Locale.getDefault())
        .apply {
            timeZone = TimeZone.getTimeZone("GMT-08:00")
        }

    override fun from(input: NewShift): Shift {

        return Shift(
            role = input.role,
            name = input.name,
            startDate = convertDate(input.start, toStartDateFormat),
            endData = convertDate(input.end, toEndDateFormat),
            color = input.color.colorName
        )
    }

    private fun convertDate(date: Date, format: SimpleDateFormat): String {
        return try {
            format.format(date)
        } catch (e: ParseException) {
            "N/A"
        }.also { println(it) }
    }
}
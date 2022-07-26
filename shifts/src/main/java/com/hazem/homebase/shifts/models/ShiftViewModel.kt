package com.hazem.homebase.shifts.models

import java.util.*

internal enum class DayPeriod {
    AM,
    PM,
}

data class ShiftTime(val hour: String, val dayPeriod: String)

data class ShiftViewModel(
    val title: String,
    val subtitle: String,
    val shiftStartDate: String,
    val shiftStartTime: ShiftTime,
    val shiftEndDate: String,
    val shiftEndTime: ShiftTime,
    val color: Color
)
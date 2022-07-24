package com.hazem.homebase.shifts.models

data class ShiftViewModel(
    val title: String,
    val subtitle: String,
    val shiftStart: String,
    val shiftEnd: String,
    val color: Color
)
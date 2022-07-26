package com.hazem.homebase.shifts.models

import java.util.*

data class NewShift(
    val name: String,
    val role: String,
    val color: Color,
    val start: Date,
    val end: Date
)
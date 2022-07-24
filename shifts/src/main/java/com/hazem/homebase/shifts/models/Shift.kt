package com.hazem.homebase.shifts.models

import com.google.gson.annotations.SerializedName

data class Shift(
    val role: String,
    val name: String,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("end_date")
    val endData: String,
    val color: String
)
package com.hazem.homebase.shifts.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Shift(
    @PrimaryKey(autoGenerate = true) val shiftId: Int = 0,
    val role: String,
    val name: String,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("end_date")
    val endData: String,
    val color: String
)
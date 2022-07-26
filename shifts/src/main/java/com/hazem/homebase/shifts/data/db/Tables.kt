package com.hazem.homebase.shifts.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EmployeeName(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)

@Entity
data class EmployeeRoles(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val role: String
)

@Entity
data class ShiftColor(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val color: String
)


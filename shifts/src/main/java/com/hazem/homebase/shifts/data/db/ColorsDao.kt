package com.hazem.homebase.shifts.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ColorsDao {
    @Query("SELECT * FROM ShiftColor")
    fun getAll(): List<ShiftColor>

    @Insert
    fun addColors(color: List<ShiftColor>)
}
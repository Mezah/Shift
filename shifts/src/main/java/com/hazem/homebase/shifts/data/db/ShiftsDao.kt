package com.hazem.homebase.shifts.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hazem.homebase.shifts.models.Shift

@Dao
interface ShiftsDao {

    @Query("SELECT * FROM Shift")
    fun getAll(): List<Shift>

    @Insert
    fun addShift(shift: Shift)

    @Insert
    fun addShifts(shifts: List<Shift>)
}

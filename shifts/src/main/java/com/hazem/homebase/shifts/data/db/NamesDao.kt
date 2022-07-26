package com.hazem.homebase.shifts.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NamesDao {
    @Query("SELECT * FROM EmployeeName")
    fun getAll(): List<EmployeeName>

    @Insert
    fun addNames(names: List<EmployeeName>)
}
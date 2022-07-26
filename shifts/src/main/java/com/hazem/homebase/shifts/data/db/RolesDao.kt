package com.hazem.homebase.shifts.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RolesDao {
    @Query("SELECT * FROM EmployeeRoles")
    fun getAll(): List<EmployeeRoles>

    @Insert
    fun addRoles(color: List<EmployeeRoles>)
}
package com.hazem.homebase.shifts.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hazem.homebase.shifts.models.Shift

@Database(
    entities = [Shift::class, EmployeeName::class, EmployeeRoles::class, ShiftColor::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shiftDao(): ShiftsDao
    abstract fun namesDao(): NamesDao
    abstract fun rolesDao(): RolesDao
    abstract fun colorsDao(): ColorsDao
}
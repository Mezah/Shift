package com.hazem.homebase.shifts.usecases

import androidx.annotation.WorkerThread
import com.hazem.homebase.shifts.data.db.AppDatabase

class LoadShiftInfoUseCase(private val db: AppDatabase) {

    @WorkerThread
    fun loadEmployeeNames(): Result<List<String>> {
        return kotlin.runCatching { db.namesDao().getAll().map { it.name }.distinct() }
    }

    @WorkerThread
    fun loadRoleLists(): Result<List<String>> {
        return kotlin.runCatching { db.rolesDao().getAll().map { it.role }.distinct() }
    }

    @WorkerThread
    fun loadColorList(): Result<List<String>> {
        return kotlin.runCatching { db.colorsDao().getAll().map { it.color }.distinct() }
    }
}
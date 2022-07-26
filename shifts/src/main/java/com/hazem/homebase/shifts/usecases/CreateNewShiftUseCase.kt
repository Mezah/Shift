package com.hazem.homebase.shifts.usecases

import com.hazem.homebase.shifts.data.db.AppDatabase
import com.hazem.homebase.shifts.data.mapper.Mapper
import com.hazem.homebase.shifts.models.NewShift
import com.hazem.homebase.shifts.models.Shift

class CreateNewShiftUseCase(
    private val db: AppDatabase,
    private val newShiftMapper: Mapper<NewShift, Shift>
) {

    fun addNewShift(newShift: NewShift) {
        val shift = newShiftMapper.from(newShift)
        db.shiftDao().addShift(shift)
    }
}
package com.hazem.homebase.shifts.usecases

import com.hazem.homebase.shifts.data.db.AppDatabase
import com.hazem.homebase.shifts.data.mapper.Mapper
import com.hazem.homebase.shifts.models.Shift
import com.hazem.homebase.shifts.models.ShiftViewModel

class LoadShiftListViewModelUseCase(
    private val db: AppDatabase,
    private val shiftMapper: Mapper<Shift, ShiftViewModel>
) {

    fun loadShiftList(): Result<List<ShiftViewModel>> {
        return kotlin.runCatching {
            db.shiftDao().getAll().map { shiftMapper.from(it) }
        }
    }
}
package com.hazem.homebase.shifts.usecases

import com.hazem.homebase.shifts.data.mapper.Mapper
import com.hazem.homebase.shifts.data.source.DataSource
import com.hazem.homebase.shifts.models.Shift
import com.hazem.homebase.shifts.models.ShiftViewModel
import com.hazem.homebase.shifts.models.Shifts

class LoadShiftViewModelListUseCase(
    private val shiftsDataSource: DataSource<Shifts>,
    private val shiftMapper: Mapper<Shift, ShiftViewModel>
) {

    fun loadShiftsViewModelList(): Result<List<ShiftViewModel>> {
        return shiftsDataSource.loadData().map { shifts ->
            shifts.shifts.map { shift -> shiftMapper.from(shift) }
        }
    }
}
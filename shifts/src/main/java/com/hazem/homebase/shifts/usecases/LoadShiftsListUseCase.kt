package com.hazem.homebase.shifts.usecases

import com.hazem.homebase.shifts.data.db.AppDatabase
import com.hazem.homebase.shifts.data.db.EmployeeName
import com.hazem.homebase.shifts.data.db.EmployeeRoles
import com.hazem.homebase.shifts.data.db.ShiftColor
import com.hazem.homebase.shifts.data.source.DataSource
import com.hazem.homebase.shifts.models.Shifts
import java.util.concurrent.Executor
import java.util.concurrent.Executors

internal class LoadShiftListUseCase(
    private val shiftsDataSource: DataSource<Shifts>,
    private val db: AppDatabase? = null
) {

    private var executor: Executor = Executors.newSingleThreadExecutor()

    fun loadShiftsViewModelList(): Result<Shifts> {
        return shiftsDataSource.loadData().map { shifts ->
            db?.apply {

                val names = shifts.shifts.map { EmployeeName(name = it.name) }.distinct()
                val colors = shifts.shifts.map { ShiftColor(color = it.color) }.distinct()
                val roles = shifts.shifts.map { EmployeeRoles(role = it.role) }.distinct()
                executor.execute {
                    if (namesDao().getAll().isEmpty())
                        namesDao().addNames(names)
                    if (colorsDao().getAll().isEmpty())
                        colorsDao().addColors(colors)
                    if (rolesDao().getAll().isEmpty())
                        rolesDao().addRoles(roles)
                }
            }
            shifts
        }
    }
}
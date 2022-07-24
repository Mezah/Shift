package com.hazem.homebase.shifts.di

import android.content.Context
import com.google.gson.Gson
import com.hazem.homebase.shifts.data.mapper.ShiftMapper
import com.hazem.homebase.shifts.data.source.ShiftsDataSource
import com.hazem.homebase.shifts.usecases.LoadShiftViewModelListUseCase
import java.lang.IllegalArgumentException
import java.lang.ref.WeakReference

object ShiftsModule : (Context) -> Unit {

    private lateinit var context: WeakReference<Context>
    private val gson = Gson()

    val loadShiftViewModelListUseCase: LoadShiftViewModelListUseCase
        get() {
            val context = context.get()
                ?: throw IllegalArgumentException(
                    "Context cannot be null, initialize the object first" +
                            ""
                )
            val dataSource = ShiftsDataSource(context, gson, com.hazem.homebase.shifts.R.raw.shifts)
            val mapper = ShiftMapper()
            return LoadShiftViewModelListUseCase(dataSource, mapper)
        }

    override fun invoke(p1: Context) {
        context = WeakReference(p1)
    }
}
package com.hazem.homebase.shifts.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.hazem.homebase.shifts.data.db.AppDatabase
import com.hazem.homebase.shifts.data.mapper.NewShiftMapper
import com.hazem.homebase.shifts.data.mapper.ShiftMapper
import com.hazem.homebase.shifts.data.source.ShiftsDataSource
import com.hazem.homebase.shifts.usecases.CreateNewShiftUseCase
import com.hazem.homebase.shifts.usecases.LoadShiftViewModelListUseCase
import java.lang.ref.WeakReference

object ShiftsModule : (Context) -> Unit {

    private const val DATABASE_NAME = "shift_db"
    private lateinit var context: WeakReference<Context>
    private val gson = Gson()
    private lateinit var appDatabase: AppDatabase

    val loadShiftViewModelListUseCase: LoadShiftViewModelListUseCase
        get() {
            val context = context.get()
                ?: throw IllegalArgumentException(
                    "Context cannot be null, initialize ShiftsModule first"
                )
            return createShiftViewModelListUseCase(context)
        }

    val createNewShiftUseCase: CreateNewShiftUseCase
        get() {
            if (!::appDatabase.isInitialized)
                throw IllegalArgumentException(
                    "Database is not created, initialize ShiftsModule first"
                )
            return createNewShiftUseCase(appDatabase)
        }

    override fun invoke(p1: Context) {
        context = WeakReference(p1)
        appDatabase = Room.databaseBuilder(
            context.get()!!,
            AppDatabase::class.java, DATABASE_NAME
        ).build()
    }

    private fun createNewShiftUseCase(appDatabase: AppDatabase): CreateNewShiftUseCase {

        val newShiftMapper = NewShiftMapper()
        return CreateNewShiftUseCase(appDatabase, newShiftMapper)
    }

    private fun createShiftViewModelListUseCase(context: Context): LoadShiftViewModelListUseCase {
        val dataSource = ShiftsDataSource(context, gson, com.hazem.homebase.shifts.R.raw.shifts)
        val mapper = ShiftMapper()
        return LoadShiftViewModelListUseCase(dataSource, mapper)
    }
}
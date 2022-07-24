package com.hazem.homebase.shifts.data.source

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson
import com.hazem.homebase.shifts.data.mapper.ShiftMapper
import com.hazem.homebase.shifts.usecases.LoadShiftViewModelListUseCase
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoadShiftViewModelListUseCaseTest {

    private val context: Context =
        ApplicationProvider.getApplicationContext<Context?>().applicationContext

    private val gson = Gson()

    @Test
    fun givenCorrectShiftDataAndMapperThenCorrectShiftViewModelReturned() {
        val ds = ShiftsDataSource(context, gson, com.hazem.homebase.shifts.R.raw.shifts)
        val mapper = ShiftMapper()
        val uc = LoadShiftViewModelListUseCase(ds, mapper)
        val vml = uc.loadShiftsViewModelList()
        Assert.assertTrue(vml.isSuccess)
        Assert.assertNotNull(vml.getOrNull())
        Assert.assertFalse(vml.getOrNull().isNullOrEmpty())
    }

    @Test
    fun givenWrongShiftDataThenResultIsFailure() {
        val ds = ShiftsDataSource(context, gson, -1)
        val mapper = ShiftMapper()
        val uc = LoadShiftViewModelListUseCase(ds, mapper)
        val vml = uc.loadShiftsViewModelList()
        Assert.assertTrue(vml.isFailure)
    }
}
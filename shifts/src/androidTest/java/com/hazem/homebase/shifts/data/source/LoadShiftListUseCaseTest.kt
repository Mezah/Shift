package com.hazem.homebase.shifts.data.source

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson
import com.hazem.homebase.shifts.R
import com.hazem.homebase.shifts.data.db.AppDatabase
import com.hazem.homebase.shifts.data.mapper.ShiftMapper
import com.hazem.homebase.shifts.usecases.LoadShiftListUseCase
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LoadShiftListUseCaseTest {

    private val context: Context =
        ApplicationProvider.getApplicationContext<Context?>().applicationContext

    private val gson = Gson()

    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
    }

    @Test
    fun givenCorrectShiftDataAndMapperThenCorrectShiftViewModelReturned() {
        val ds = ShiftsDataSource(context, gson, R.raw.shifts)
        val uc = LoadShiftListUseCase(ds)
        val vml = uc.loadShiftsViewModelList()
        Assert.assertTrue(vml.isSuccess)
        Assert.assertNotNull(vml.getOrNull())
        Assert.assertFalse(vml.getOrNull()?.shifts.isNullOrEmpty())
    }

    @Test
    fun givenWrongShiftDataThenResultIsFailure() {
        val ds = ShiftsDataSource(context, gson, -1)
        val uc = LoadShiftListUseCase(ds)
        val vml = uc.loadShiftsViewModelList()
        Assert.assertTrue(vml.isFailure)
    }

    @Test
    fun whenLoadingInformationFromJsonFileNamesColorsAndRolesAreSavedInDb() {
        val shiftsDataSource = ShiftsDataSource(context, gson, R.raw.shifts)
        val uc = LoadShiftListUseCase(shiftsDataSource, db)
        val vml = uc.loadShiftsViewModelList()
        Assert.assertTrue(vml.isSuccess)
        val shifts = db.shiftDao().getAll()
        val names = db.namesDao().getAll()
        val colors = db.colorsDao().getAll()
        val roles = db.rolesDao().getAll()
        Assert.assertTrue(shifts.isNotEmpty())
        Assert.assertTrue(names.isNotEmpty())
        Assert.assertTrue(colors.isNotEmpty())
        Assert.assertTrue(roles.isNotEmpty())
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}
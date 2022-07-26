package com.hazem.homebase.shifts.data.source

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hazem.homebase.shifts.data.db.AppDatabase
import com.hazem.homebase.shifts.data.mapper.NewShiftMapper
import com.hazem.homebase.shifts.models.Color
import com.hazem.homebase.shifts.models.NewShift
import com.hazem.homebase.shifts.usecases.CreateNewShiftUseCase
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@RunWith(AndroidJUnit4::class)
class CreateNewShiftUseCaseTest {

    private lateinit var db: AppDatabase
    private lateinit var newShiftUseCase: CreateNewShiftUseCase
    private val fromDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss XXX", Locale.getDefault())

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()

        newShiftUseCase = CreateNewShiftUseCase(db, NewShiftMapper())
    }

    @Test
    fun createNewShiftInsertedCorrectlyInDb() {
        val startDate = "2018-04-21 9:00:00 -08:00"
        val endDate = "2018-4-21 14:00:00 -08:00"
        val sDate = fromDateFormat.parse(startDate)
        val eDate = fromDateFormat.parse(endDate)

        val newShift = NewShift(
            name = "TestName",
            role = "TestRole",
            start = sDate,
            end = eDate,
            color = Color.RED
        )

        newShiftUseCase.addNewShift(newShift)
        val shifts = db.shiftDao().getAll()

        Assert.assertTrue(shifts.isNotEmpty())
        Assert.assertTrue(shifts.size == 1)
        val addedShift = shifts.first()
        Assert.assertEquals("TestName", addedShift.name)
        Assert.assertEquals("TestRole", addedShift.role)
        Assert.assertEquals(Color.RED.colorName, addedShift.color)
        Assert.assertEquals("2018-04-21 9:00:00 -08:00", addedShift.startDate)
        Assert.assertEquals("2018-4-21 14:00:00 -08:00", addedShift.endData)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

}
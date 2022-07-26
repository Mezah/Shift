package com.hazem.homebase.shifts.data.source

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson
import com.hazem.homebase.shifts.R.raw
import com.hazem.homebase.shifts.data.db.AppDatabase
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ShiftsDataSourceTest {
    private val context: Context =
        ApplicationProvider.getApplicationContext<Context?>().applicationContext
    private val gson = Gson()
    private lateinit var shiftsDataSource: ShiftsDataSource


    @Test
    fun givenValidResourceFileContainsShiftListWhenLoadingListOfShiftItShouldNotBeEmpty() {
        shiftsDataSource = ShiftsDataSource(context, gson, raw.shifts)
        val data = shiftsDataSource.loadData()
        assertTrue(data.isSuccess)
        val shifts = data.getOrNull()
        assertNotNull(shifts)
        assertFalse(shifts?.shifts.isNullOrEmpty())

    }

    @Test
    fun givenInvValidResourceFileContainsShiftListWhenLoadingListOfShiftWillReturnFailResult() {
        shiftsDataSource = ShiftsDataSource(context, gson, 0)
        val data = shiftsDataSource.loadData()
        assertTrue(data.isFailure)
    }
}
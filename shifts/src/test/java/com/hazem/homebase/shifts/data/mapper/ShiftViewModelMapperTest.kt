package com.hazem.homebase.shifts.data.mapper

import com.hazem.homebase.shifts.models.Color
import com.hazem.homebase.shifts.models.NewShift
import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class ShiftViewModelMapperTest {

    private val newShiftMapper = NewShiftMapper()
    private val fromDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss XXX", Locale.getDefault())

    @Test
    fun `given a shift with a valid data when creating a new shift then it will contains correct data`() {
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
        val shift = newShiftMapper.from(newShift)

        Assert.assertEquals("TestName", shift.name)
        Assert.assertEquals("TestRole", shift.role)
        Assert.assertEquals("2018-04-21 9:00:00 -08:00", shift.startDate)
        Assert.assertEquals("2018-4-21 14:00:00 -08:00", shift.endData)
        Assert.assertEquals(Color.RED.colorName, shift.color)
    }
}
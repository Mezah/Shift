package com.hazem.homebase.shifts.data.mapper

import com.hazem.homebase.shifts.models.Color
import com.hazem.homebase.shifts.models.DayPeriod
import com.hazem.homebase.shifts.models.Shift
import com.hazem.homebase.shifts.models.ShiftTime
import org.junit.Assert
import org.junit.Test

class ShiftMapperTest {

    private val shiftMapper = ShiftMapper()

    @Test
    fun `given a shift with a valid data when creating a shift view model then it will contains correct data`() {
        val shift = Shift(
            role = "TestRole",
            name = "TestName",
            startDate = "2018-04-21 9:00:00 -08:00",
            endData = "2018-4-21 14:00:00 -08:00",
            color = "red"
        )
        val shiftViewModel = shiftMapper.from(shift)
        Assert.assertEquals("TestName", shiftViewModel.title)
        Assert.assertEquals("TestRole", shiftViewModel.subtitle)
        Assert.assertEquals("Sat, April 21", shiftViewModel.shiftStartDate)
        Assert.assertEquals("Sat, April 21", shiftViewModel.shiftEndDate)
        Assert.assertEquals(ShiftTime("9",DayPeriod.AM.toString()), shiftViewModel.shiftStartTime)
        Assert.assertEquals(ShiftTime("14",DayPeriod.PM.toString()), shiftViewModel.shiftEndTime)
        Assert.assertEquals(Color.RED, shiftViewModel.color)
    }

    @Test
    fun `given a shift with an different date format when creating a shift view model then date will not be available`() {
        val shift = Shift(
            role = "TestRole",
            name = "TestName",
            startDate = "2018/04/20 13:00:00",
            endData = "2018.4.20 11:00:00 -08:00",
            color = "red"
        )
        val shiftViewModel = shiftMapper.from(shift)
        Assert.assertEquals("TestName", shiftViewModel.title)
        Assert.assertEquals("TestRole", shiftViewModel.subtitle)
        Assert.assertEquals("N/A", shiftViewModel.shiftStartDate)
        Assert.assertEquals("N/A", shiftViewModel.shiftEndDate)
        Assert.assertEquals("NA", shiftViewModel.shiftStartTime.hour)
        Assert.assertEquals("NA", shiftViewModel.shiftStartTime.dayPeriod)
        Assert.assertEquals("NA", shiftViewModel.shiftEndTime.hour)
        Assert.assertEquals("NA", shiftViewModel.shiftEndTime.dayPeriod)
        Assert.assertEquals(Color.RED, shiftViewModel.color)
    }

    @Test
    fun `given a shift with an wrong color name when creating a shift view model then object will use transparent color`() {
        val shift = Shift(
            role = "TestRole",
            name = "TestName",
            startDate = "2018-04-20 13:00:00 -08:00",
            endData = "2018-4-20 11:00:00 -08:00",
            color = "NoColor"
        )
        val shiftViewModel = shiftMapper.from(shift)
        Assert.assertEquals("TestName", shiftViewModel.title)
        Assert.assertEquals("TestRole", shiftViewModel.subtitle)
        Assert.assertEquals("Fri, April 20", shiftViewModel.shiftStartDate)
        Assert.assertEquals("Fri, April 20", shiftViewModel.shiftEndDate)
        Assert.assertEquals(Color.TRANSPARENT, shiftViewModel.color)
    }
}
package com.hazem.homebase.shifts.data.mapper

import com.hazem.homebase.shifts.models.Color
import com.hazem.homebase.shifts.models.Shift
import org.junit.Assert
import org.junit.Test

class ShiftMapperTest {

    private val shiftMapper = ShiftMapper()

    @Test
    fun `given a shift with a valid data when creating a shift view model then it will contains correct data`() {
        val shift = Shift(
            role = "TestRole",
            name = "TestName",
            startDate = "2018-04-20 13:00:00 -08:00",
            endData = "2018-4-20 11:00:00 -08:00",
            color = "red"
        )
        val shiftViewModel = shiftMapper.from(shift)
        Assert.assertEquals("TestName's Shift", shiftViewModel.title)
        Assert.assertEquals("TestRole", shiftViewModel.subtitle)
        Assert.assertEquals("Fri, April 20 PM", shiftViewModel.shiftStart)
        Assert.assertEquals("Fri, April 20 AM", shiftViewModel.shiftEnd)
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
        Assert.assertEquals("TestName's Shift", shiftViewModel.title)
        Assert.assertEquals("TestRole", shiftViewModel.subtitle)
        Assert.assertEquals("N/A", shiftViewModel.shiftStart)
        Assert.assertEquals("N/A", shiftViewModel.shiftEnd)
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
        Assert.assertEquals("TestName's Shift", shiftViewModel.title)
        Assert.assertEquals("TestRole", shiftViewModel.subtitle)
        Assert.assertEquals("Fri, April 20 PM", shiftViewModel.shiftStart)
        Assert.assertEquals("Fri, April 20 AM", shiftViewModel.shiftEnd)
        Assert.assertEquals(Color.TRANSPARENT, shiftViewModel.color)
    }
}
package com.hazem.homebase.shiftapp

import android.app.Application
import com.hazem.homebase.shifts.di.ShiftsModule

class ShiftApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // initialize ShiftModule
        ShiftsModule(this)
    }
}
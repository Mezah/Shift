package com.hazem.homebase.shifts.data.source

import android.content.Context
import androidx.annotation.RawRes
import com.google.gson.Gson
import com.hazem.homebase.shifts.models.Shifts

internal class ShiftsDataSource(
    private val context: Context,
    private val gson: Gson,
    @RawRes private val resourceId: Int
) : DataSource<Shifts> {

    override fun loadData(): Result<Shifts> {
        return kotlin.runCatching {
            context.resources.openRawResource(resourceId)
                .bufferedReader().use { buffer ->
                    return@use gson.fromJson(buffer.readText(), Shifts::class.java)
                }
        }
    }
}
package com.hazem.homebase.shifts.data.source

interface DataSource<out T> {
    fun loadData(): Result<T>
}
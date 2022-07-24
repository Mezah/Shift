package com.hazem.homebase.shifts.data.mapper

interface Mapper<Input, Output> {

    fun from(input: Input): Output
}
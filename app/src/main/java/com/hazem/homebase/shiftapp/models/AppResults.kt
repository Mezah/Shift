package com.hazem.homebase.shiftapp.models

sealed class AppResults<out T> {

    data class Success<T>(val result: T) : AppResults<T>()
    object Failure : AppResults<Nothing>()
}
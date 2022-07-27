package com.hazem.homebase.shiftapp.models

sealed class AppResults<out T> {

    data class Success<T>(val result: T) : AppResults<T>()

    object LoadingError : Failure()

    sealed class Failure : AppResults<Nothing>()

    sealed class ValidationFailure : Failure()
    object EmptyName : ValidationFailure()
    object EmptyRole : ValidationFailure()
    object EmptyColor : ValidationFailure()
    object EmptyStartDate : ValidationFailure()
    object EmptyEndDate : ValidationFailure()


}
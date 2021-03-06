package com.duyha.facebook.data.model

sealed class Result<out T> {

    data class Success<T>(val data: T) : Result<T>()
    data class Failure(val e: Error) : Result<Nothing>()
}

package com.duyha.facebook.data.model

import android.util.Log
import java.io.IOException
import java.lang.Exception

sealed class Result<out T> {

    data class Success<T>(val data: T) : Result<T>()
    data class Failure(val e: Error) : Result<Nothing>()
}

inline fun <T> handleError(function: () -> T) : Result<T> {
    return try {
        val result = function()
        Result.Success(result)
    } catch (e: Exception) {
        Log.e("Error", e.stackTraceToString())
        when (e) {
            is IOException -> {
                Result.Failure(e = Error.NetworkConnection)
            }
            else -> {
                Result.Failure(e = Error.UnexpectedError)
            }
        }
    }
}

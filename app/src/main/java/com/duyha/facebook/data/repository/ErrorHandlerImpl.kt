package com.duyha.facebook.data.repository

import com.duyha.facebook.data.model.Error
import com.duyha.facebook.data.model.Result
import java.io.IOException
import java.lang.Exception

class ErrorHandlerImpl : ErrorHandler {
    override suspend fun <T> handleError(block: suspend () -> T): Result<T> {
        return try {
            val result = block.invoke()
            Result.Success(data = result)
        } catch (e: Exception) {
            val error = getError(e)
            Result.Failure(e = error)
        }
    }

    private fun getError(throwable: Throwable): Error {
        return when(throwable) {
            is IOException -> Error.NetworkConnection
            else -> Error.UnexpectedError
        }
    }
}
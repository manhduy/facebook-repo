package com.duyha.facebook.data.repository

import com.duyha.facebook.data.model.Error
import com.duyha.facebook.data.model.Result
import java.io.IOException
import java.lang.Exception

class ErrorHandlerImpl : ErrorHandler {

    override fun getError(throwable: Throwable): Error {
        return when(throwable) {
            is IOException -> Error.NetworkConnection
            else -> Error.UnexpectedError
        }
    }
}
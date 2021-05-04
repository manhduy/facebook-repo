package com.duyha.facebook.data.repository

import com.duyha.facebook.data.model.Result

interface ErrorHandler {

    suspend fun <T> handleError(block: suspend () -> T): Result<T>
}
package com.duyha.facebook.data.repository

import com.duyha.facebook.data.model.Error

interface ErrorHandler {

    fun getError(throwable: Throwable): Error
}
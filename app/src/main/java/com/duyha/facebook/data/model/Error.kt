package com.duyha.facebook.data.model

sealed class Error {
    object NetworkConnection : Error()
    object UnexpectedError: Error()
}

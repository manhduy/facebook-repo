package com.duyha.facebook.utils

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

fun String.toDateTime(format: String): Date? {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return try {
        dateFormat.parse(this)
    } catch (e: Exception) {
        null
    }
}

fun Date.formatToString(format: String): String {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.format(this)
}

fun String.serverDateToLocalDate(): String {
    val date = this.toDateTime("EEE, dd MMM yyyy HH:mm:ss z")
    return date?.formatToString("EEE, dd MMM yyyy") ?: ""
}
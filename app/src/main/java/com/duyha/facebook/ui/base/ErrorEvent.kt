package com.duyha.facebook.ui.base

import com.duyha.facebook.R

enum class ErrorEvent {
    NO_NETWORK_CONNECTION {
        override fun stringResId(): Int = R.string.err_msg_no_internet_connection
    },
    UNEXPECTED_ERROR_OCCURRED {
        override fun stringResId(): Int = R.string.err_msg_unexpected_error_occurred
    };

    abstract fun stringResId(): Int
}
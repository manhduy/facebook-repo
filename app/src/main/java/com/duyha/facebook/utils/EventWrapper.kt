package com.duyha.facebook.utils

open class Event<out T>(private val content: T) {

    private var _hasBeenHandled = false
    val hasBeenHanded: Boolean
        get() = _hasBeenHandled

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (_hasBeenHandled) {
            null
        } else {
            _hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}
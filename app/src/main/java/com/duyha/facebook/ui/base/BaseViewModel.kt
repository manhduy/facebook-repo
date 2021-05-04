package com.duyha.facebook.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.duyha.facebook.data.model.Error
import com.duyha.facebook.data.model.Result
import com.duyha.facebook.utils.Event

abstract class BaseViewModel : ViewModel() {

    protected val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _err  = MutableLiveData<Event<ErrorEvent>>()
    val err: LiveData<Event<ErrorEvent>>
        get() = _err

    fun handleError(failure: Result.Failure) {
        when (failure.e) {
            is Error.NetworkConnection -> _err.postValue(Event(ErrorEvent.NO_NETWORK_CONNECTION))
            is Error.UnexpectedError -> _err.postValue(Event(ErrorEvent.UNEXPECTED_ERROR_OCCURRED))
        }
    }

    suspend fun <T> handleError(function: suspend () ->  Result<T>, onSuccess: (T) -> Unit) {
        Result
        _loading.postValue(false)
        when (val result = function.invoke()) {
            is Result.Success -> onSuccess(result.data)
            is  Result.Failure -> handleError(result)
        }
        _loading.postValue(false)
    }
}
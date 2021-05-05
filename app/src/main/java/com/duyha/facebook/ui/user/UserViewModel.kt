package com.duyha.facebook.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.duyha.facebook.data.model.Result
import com.duyha.facebook.data.model.User
import com.duyha.facebook.data.repository.RepoRepository
import com.duyha.facebook.data.repository.UserRepository
import com.duyha.facebook.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    repoRepository: RepoRepository
) : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    val repoFlow = repoRepository.getPager().flow.cachedIn(viewModelScope)

    fun getUser() {
        viewModelScope.launch {
            when (val result = userRepository.getUser()) {
                is Result.Success<User> -> {
                    val user = result.data
                    _user.postValue(user)
                }
                else -> handleError(result as Result.Failure)
            }
        }
    }
}
package com.duyha.facebook.data.repository

import com.duyha.facebook.data.model.Result
import com.duyha.facebook.data.model.User

interface UserRepository {
    suspend fun getUser(): Result<User>
}
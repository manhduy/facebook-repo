package com.duyha.facebook.data.repository

import com.duyha.facebook.data.model.Result
import com.duyha.facebook.data.model.User
import com.duyha.facebook.data.remote.GithubApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val githubApi: GithubApi,
    private val errorHandler: ErrorHandler,
    private val ioDispatcher: CoroutineDispatcher
) : UserRepository {

    override suspend fun getUser(): Result<User> = withContext(ioDispatcher) {
        return@withContext errorHandler.handleError {
            val response = githubApi.getUser()
            User.fromRemoteResponse(response)
        }
    }
}
package com.duyha.facebook.data.repository

import com.duyha.facebook.data.model.Result
import com.duyha.facebook.data.model.User
import com.duyha.facebook.data.remote.GithubApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val githubApi: GithubApi,
    private val errorHandler: ErrorHandler,
    private val ioDispatcher: CoroutineDispatcher
) : UserRepository {

    override suspend fun getUser(): Result<User> = withContext(ioDispatcher) {
        return@withContext try {
            val response = githubApi.getUser()
            Result.Success(data = User.fromRemoteResponse(response))
        } catch (e: Exception) {
            val error = errorHandler.getError(e)
            Result.Failure(e = error)
        }
    }
}
package com.duyha.facebook.data.repository

import com.duyha.facebook.data.model.Error
import com.duyha.facebook.data.remote.GithubApi
import com.duyha.facebook.data.remote.dto.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import com.duyha.facebook.data.model.Result
import com.duyha.facebook.data.model.User
import java.io.IOException

class UserRepositoryTest {

    lateinit var repository: UserRepositoryImpl
    lateinit var api: GithubApi
    lateinit var errorHandler: ErrorHandler

    private val userResponse = UserResponse(
            avatarUrl = "avatarurl",
            name = "Facebook",
            bio = "We are facebook",
            location = "USA",
            blog = "facebook.com"
    )

    @Before
    fun setUp() {
        api = mock()
        errorHandler = mock()
        repository = UserRepositoryImpl(api, errorHandler, Dispatchers.Default)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getUser_WhenApiReturnUserResponse_ShouldReturnSuccessUser() = runBlocking {
        //Given
        whenever(api.getUser()).thenReturn(userResponse)
        //When
        val result = repository.getUser()
        //Then
        assertTrue(result is Result.Success)
        assertThat((result as Result.Success).data, equalTo(User.fromRemoteResponse(userResponse)))
    }

    @Test
    fun getUser_WhenApiReturnException_ShouldReturnError() = runBlocking {
        //Given
        whenever(api.getUser()).thenAnswer { throw IOException() }
        whenever(errorHandler.getError(any())).thenReturn(Error.NetworkConnection)
        //When
        val result = repository.getUser()
        //Then
        assertTrue(result is Result.Failure)
        assertThat((result as Result.Failure).e, equalTo(Error.NetworkConnection))
    }
}
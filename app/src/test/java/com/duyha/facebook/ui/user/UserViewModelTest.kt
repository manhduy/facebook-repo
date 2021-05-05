package com.duyha.facebook.ui.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.*
import com.duyha.facebook.data.model.Error
import com.duyha.facebook.data.model.Repo
import com.duyha.facebook.data.repository.RepoRepository
import com.duyha.facebook.data.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*
import com.duyha.facebook.data.model.Result
import com.duyha.facebook.data.model.User
import com.duyha.facebook.ui.base.ErrorEvent
import com.duyha.facebook.utils.Constants
import com.duyha.facebook.utils.getOrAwaitValue
import java.io.IOException

class UserViewModelTest {

    private lateinit var viewModel: UserViewModel
    private lateinit var userRepository: UserRepository
    private lateinit var repoRepository: RepoRepository

    private val success = Result.Success(
            data = User(
                    avatarUrl = "avatarUrl",
                    name = "Facebook",
                    bio = "bio",
                    location = "USA",
                    blog = "facebook.com"
            ))
    private val error = Result.Failure(Error.NetworkConnection)

    private val repoList = listOf(
        Repo(
            id = 1,
            fullName = "react-native",
            description = "React framework",
            language = "JavaScript",
            stargazersCount = 10,
            issuesCount = 9,
            forksCount = 8,
            updatedAt = "Apr 12, 2021"
        )
    )

    private val successPager = Pager(PagingConfig(Constants.REPO_PAGE_SIZE)) {
        object : PagingSource<Int, Repo>() {
            override fun getRefreshKey(state: PagingState<Int, Repo>): Int? = null

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> =
                LoadResult.Page(
                    data = repoList,
                    prevKey = null,
                    nextKey = null
                )

        }
    }

    private val errorPager = Pager(PagingConfig(Constants.REPO_PAGE_SIZE)) {
        object : PagingSource<Int, Repo>() {
            override fun getRefreshKey(state: PagingState<Int, Repo>): Int? = null

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> =
                LoadResult.Error(IOException())

        }
    }

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        userRepository = mock()
        repoRepository = mock()
        whenever(repoRepository.getPager()).thenReturn(successPager)
        viewModel = UserViewModel(userRepository, repoRepository)

    }

    @After
    fun tearDown() {
    }

    @Test
    fun getUser_RepoReturnSuccess_ShowUser() = runBlocking {
        //Given
        whenever(userRepository.getUser()).thenReturn(success)
        //When
        viewModel.getUser()
        //Then
        assertThat(viewModel.user.getOrAwaitValue(), equalTo(success.data))
    }

    @Test
    fun getUser_RepoReturnError_ShowErrorMessage() = runBlocking {
        //Given
        whenever(userRepository.getUser()).thenReturn(error)
        //When
        viewModel.getUser()
        //Then
        assertThat(viewModel.err.getOrAwaitValue().getContentIfNotHandled(),
                equalTo(ErrorEvent.NO_NETWORK_CONNECTION))
    }
}
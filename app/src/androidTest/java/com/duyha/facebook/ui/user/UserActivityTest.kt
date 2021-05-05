package com.duyha.facebook.ui.user

import androidx.paging.*
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.duyha.facebook.data.model.Error
import com.duyha.facebook.data.model.Repo
import com.duyha.facebook.data.model.Result
import com.duyha.facebook.data.model.User
import com.duyha.facebook.data.repository.RepoRepository
import com.duyha.facebook.data.repository.UserRepository
import com.duyha.facebook.di.RepositoryModule
import com.duyha.facebook.utils.Constants
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import java.io.IOException


@HiltAndroidTest
@UninstallModules(RepositoryModule::class)
@RunWith(AndroidJUnit4::class)
class UserActivityTest {
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

    @BindValue
    val userRepository: UserRepository = mock(UserRepository::class.java)

    @BindValue
    val repoRepository: RepoRepository = mock(RepoRepository::class.java)

    @BindValue
    val ioDispatcher: CoroutineDispatcher = Dispatchers.Main

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun onResume_WhenUserRepositoryReturnSuccess_ShowUser() {
        runBlocking {
            //Given
            `when`(repoRepository.getPager()).thenReturn(successPager)
            `when`(userRepository.getUser()).thenReturn(success)
            launchActivity<UserActivity>()
            //When
            //Then
            onView(withText("Facebook")).check(matches(isDisplayed()))
            onView(withText("bio")).check(matches(isDisplayed()))
            onView(withText("USA")).check(matches(isDisplayed()))
            onView(withText("facebook.com")).check(matches(isDisplayed()))
        }
    }

    @Test
    fun onResume_WhenRepoReturnError_ShowError() {
        runBlocking {
            //Given
            `when`(repoRepository.getPager()).thenReturn(successPager)
            `when`(userRepository.getUser()).thenReturn(error)
            launchActivity<UserActivity>()
            //When
            //Then
            onView(withText("No internet connection")).check(matches(isDisplayed()))
        }
    }

    @Test
    fun onResume_WhenRepoRepositoryReturnSuccess_ShowRepoList() {
        runBlocking {
            //Given
            `when`(repoRepository.getPager()).thenReturn(successPager)
            `when`(userRepository.getUser()).thenReturn(success)
            //When
            launchActivity<UserActivity>()
            //Then
            onView(withText("react-native")).check(matches(isDisplayed()))
            onView(withText("React framework")).check(matches(isDisplayed()))
            onView(withText("JavaScript")).check(matches(isDisplayed()))
            onView(withText("10")).check(matches(isDisplayed()))
            onView(withText("9")).check(matches(isDisplayed()))
            onView(withText("8")).check(matches(isDisplayed()))
            onView(withText("Updated on Apr 12, 2021")).check(matches(isDisplayed()))
        }
    }

    @Test
    fun onResume_WhenRepoRepositoryReturnError_ShowRetry() {
        runBlocking {
            //Given
            `when`(repoRepository.getPager()).thenReturn(errorPager)
            `when`(userRepository.getUser()).thenReturn(success)
            //When
            launchActivity<UserActivity>()
            //Then
            onView(withText("Retry")).check(matches(isDisplayed()))
        }
    }
}
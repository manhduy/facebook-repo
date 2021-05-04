package com.duyha.facebook.di

import com.duyha.facebook.data.remote.GithubApi
import com.duyha.facebook.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideErrorHandler(): ErrorHandler = ErrorHandlerImpl()

    @Provides
    fun provideUserRepository(githubApi: GithubApi,
                              errorHandler: ErrorHandler,
                              ioDispatcher: CoroutineDispatcher): UserRepository =
            UserRepositoryImpl(githubApi, errorHandler, ioDispatcher)

    @Provides
    fun provideRepoRepository(githubApi: GithubApi): RepoRepository = RepoRepositoryImpl(githubApi)
}
package com.duyha.facebook.data.remote

import com.duyha.facebook.data.remote.dto.RepoResponse
import com.duyha.facebook.data.remote.dto.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("users/facebook")
    suspend fun getUser(): UserResponse

    @GET("users/facebook/repos")
    suspend fun getRepositories(@Query("per_page") perPage: Int,
                             @Query("page") page: Int) : List<RepoResponse>
}
package com.duyha.facebook.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RepoResponse(
    @SerializedName("id") val id : Long,
    @SerializedName("full_name") val fullName : String,
    @SerializedName("description") val description : String,
    @SerializedName("language") val language : String?,
    @SerializedName("stargazers_count") val stargazersCount : Int,
    @SerializedName("open_issues_count") val issuesCount : Int,
    @SerializedName("forks_count") val forksCount : Int,
    @SerializedName("updated_at") val updatedAt : String,
    )
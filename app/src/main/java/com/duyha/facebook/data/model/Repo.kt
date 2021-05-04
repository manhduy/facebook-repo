package com.duyha.facebook.data.model

import com.duyha.facebook.data.remote.dto.RepoResponse
import com.duyha.facebook.utils.formatToString
import com.duyha.facebook.utils.toDateTime


data class Repo(
    val id : Long,
    val fullName : String,
    val description : String,
    val language : String?,
    val stargazersCount : Int,
    val issuesCount : Int,
    val forksCount : Int,
    val updatedAt : String,
) {
    companion object {
        fun fromRemoteResponse(response: RepoResponse): Repo {
            val updateDate = response.updatedAt
                .toDateTime("yyyy-MM-dd'T'HH:mm:ss")
                ?.formatToString("MMM dd, yyyy") ?: ""
            return Repo(
                id = response.id,
                fullName = response.fullName,
                description = response.description,
                language = response.language,
                stargazersCount = response.stargazersCount,
                issuesCount = response.issuesCount,
                forksCount = response.forksCount,
                updatedAt = updateDate,
            )
        }
    }
}
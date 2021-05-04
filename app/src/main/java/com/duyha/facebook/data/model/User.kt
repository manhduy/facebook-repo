package com.duyha.facebook.data.model

import com.duyha.facebook.data.remote.dto.UserResponse

data class User(
    val avatarUrl : String,
    val name : String,
    val bio : String,
    val location : String,
    val blog : String,
) {
    companion object {
        fun fromRemoteResponse(response: UserResponse): User {
            return User(
                avatarUrl = response.avatarUrl,
                name = response.name,
                bio = response.bio,
                location = response.location,
                blog = response.blog,
            )
        }
    }
}
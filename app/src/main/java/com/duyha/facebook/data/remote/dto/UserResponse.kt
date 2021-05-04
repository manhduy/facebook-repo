package com.duyha.facebook.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("avatar_url") val avatarUrl : String,
    @SerializedName("name") val name : String,
    @SerializedName("bio") val bio : String,
    @SerializedName("location") val location : String,
    @SerializedName("blog") val blog : String,
)
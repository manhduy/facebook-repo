package com.duyha.facebook.data.model


data class Repo(
    val id : Long,
    val fullName : String,
    val description : String,
    val language : String,
    val htmlUrl : String,
    val updatedAt : String,
    val stargazersCount : Int,
    val issueCount : Int,
    val forksCount : Int
)
package com.duyha.facebook.data.repository

import androidx.paging.Pager
import com.duyha.facebook.data.model.Repo

interface RepoRepository {

    fun getPager(): Pager<Int, Repo>
}
package com.duyha.facebook.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.duyha.facebook.data.model.Repo
import com.duyha.facebook.data.remote.GithubApi
import com.duyha.facebook.utils.Constants
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RepoRepositoryImpl constructor(
        private val githubApi: GithubApi
): RepoRepository {
    override fun createPagingSource(): Pager<Int, Repo> {
        return Pager(PagingConfig(Constants.REPO_PAGE_SIZE)) {
            RepoPagingSource(githubApi)
        }
    }
}

class RepoPagingSource @Inject constructor(
        private val githubApi: GithubApi,
) : PagingSource<Int, Repo>() {
    override suspend fun load(
            params: LoadParams<Int>
    ): LoadResult<Int, Repo> {
        return try {
            val page = params.key ?: 0
            val response = githubApi.getRepositories(Constants.REPO_PAGE_SIZE, page)
            val repositories = response.map {
                Repo.fromRemoteResponse(it)
            }
            val nextKey = if (repositories.size == Constants.REPO_PAGE_SIZE) page + 1 else null
            LoadResult.Page(
                    data = repositories,
                    prevKey = null,
                    nextKey = nextKey
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
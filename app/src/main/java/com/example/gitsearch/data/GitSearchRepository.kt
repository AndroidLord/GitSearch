package com.example.gitsearch.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.gitsearch.data.local.GitSearchDao
import com.example.gitsearch.data.local.entity.RepositoryEntity
import com.example.gitsearch.data.remote.RepositoryRemoteMediatorFactory
import com.example.gitsearch.data.remote.api.GithubApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class GitSearchRepository @Inject constructor(
    private val gitRepoMediator: RepositoryRemoteMediatorFactory,
    private val gitSearchDao: GitSearchDao,
    private val githubApiService: GithubApiService
) {

    fun getGitRepoSearchPagingSource(query: String): Flow<PagingData<RepositoryEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            remoteMediator = gitRepoMediator.create(query),
            pagingSourceFactory = {
                gitSearchDao.getGitRepoSearchPagingSource()
            }
        ).flow
    }

    suspend fun getGitRepoDetails(owner: String, repo: String) = withContext(Dispatchers.IO) {
        githubApiService.getRepositoryDetails(owner, repo, 1)
    }

    suspend fun getContributors(owner: String, repo: String) = withContext(Dispatchers.IO) {
        githubApiService.getContributors(owner, repo, 1)
    }

    suspend fun getUserRepositories(username: String) = withContext(Dispatchers.IO) {
        githubApiService.getUserRepositories(username, 1)
    }


}
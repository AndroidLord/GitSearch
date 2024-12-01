package com.example.gitsearch.data.remote

import com.example.gitsearch.data.local.GitSearchDatabase
import com.example.gitsearch.data.remote.api.GithubApiService
import javax.inject.Inject

class RepositoryRemoteMediatorFactory @Inject constructor(
    private val gitDb: GitSearchDatabase,
    private val repoApi: GithubApiService
) {
    fun create(query: String): RepositoryRemoteMediator {
        return RepositoryRemoteMediator(gitDb, repoApi, query)
    }
}

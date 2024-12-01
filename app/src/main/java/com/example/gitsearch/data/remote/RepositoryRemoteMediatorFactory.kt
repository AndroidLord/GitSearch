package com.example.gitsearch.data.remote

import android.content.Context
import com.example.gitsearch.data.local.GitSearchDatabase
import com.example.gitsearch.data.remote.api.GithubApiService
import javax.inject.Inject

class RepositoryRemoteMediatorFactory @Inject constructor(
    private val gitDb: GitSearchDatabase,
    private val repoApi: GithubApiService
) {
    fun create(query: String, context: Context): RepositoryRemoteMediator {
        return RepositoryRemoteMediator(context, gitDb, repoApi, query)
    }
}

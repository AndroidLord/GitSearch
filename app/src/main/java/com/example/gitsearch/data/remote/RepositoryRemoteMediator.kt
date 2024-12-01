@file:OptIn(ExperimentalPagingApi::class)

package com.example.gitsearch.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.gitsearch.data.local.GitSearchDatabase
import com.example.gitsearch.data.local.entity.RepositoryEntity
import com.example.gitsearch.data.mappers.toRepositoryEntity
import com.example.gitsearch.data.remote.api.GithubApiService
import okio.IOException
import retrofit2.HttpException

class RepositoryRemoteMediator(
    private val gitDb: GitSearchDatabase,
    private val repoApi: GithubApiService,
    private val query: String,
) : RemoteMediator<Int, RepositoryEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepositoryEntity>
    ): MediatorResult {
        return try {

            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    lastItem.id
                }
            }

            val gitRepository = repoApi.searchRepositories(query, loadKey)

            gitDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    gitDb.gitSearchDao().clearAll()
                }
                val gitRepositoryEntity = gitRepository.repositories.map {
                    it.toRepositoryEntity()
                }
                gitDb.gitSearchDao().upsertRepository(gitRepositoryEntity)
            }

            MediatorResult.Success(endOfPaginationReached = gitRepository.repositories.isEmpty())

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }

    }

}
@file:OptIn(ExperimentalPagingApi::class)

package com.example.gitsearch.data.remote

import android.content.Context
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.gitsearch.core.NetworkUtils
import com.example.gitsearch.data.local.GitSearchDatabase
import com.example.gitsearch.data.local.entity.RepositoryEntity
import com.example.gitsearch.data.mappers.toRepositoryEntity
import com.example.gitsearch.data.remote.api.GithubApiService
import retrofit2.HttpException
import java.io.IOException

class RepositoryRemoteMediator(
    private val context: Context,
    private val gitDb: GitSearchDatabase,
    private val repoApi: GithubApiService,
    private val query: String,
) : RemoteMediator<Int, RepositoryEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepositoryEntity>
    ): MediatorResult {
        return try {

            val isOnline = NetworkUtils.isNetworkAvailable(context)

            if (!isOnline && loadType == LoadType.REFRESH && gitDb.gitSearchDao().isQueryInDb(query)) {
                // Skip fetching and use cached data
                return MediatorResult.Success(endOfPaginationReached = false)
            }

            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.APPEND -> {
                    val lastItem = state.pages.lastOrNull()?.data?.lastOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        // Calculate the next page number
                        state.pages.size + 1
                    }
                }

                else -> return MediatorResult.Success(endOfPaginationReached = true)
            }

            val gitRepository = repoApi.searchRepositories(
                query = query,
                page = loadKey,
                perPage = state.config.pageSize
            )

            gitDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    gitDb.gitSearchDao().clearQueryData(query)
                }
                val gitRepositoryEntity = gitRepository.repositories.map {
                    it.toRepositoryEntity(query)
                }
                gitDb.gitSearchDao().upsertRepository(gitRepositoryEntity)
            }
            val isEndOfPagination = gitRepository.repositories.isEmpty()
            MediatorResult.Success(endOfPaginationReached = isEndOfPagination)


        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }

    }

}
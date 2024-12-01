package com.example.gitsearch.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.gitsearch.data.local.entity.RepositoryEntity

@Dao
interface GitSearchDao {

    @Query("SELECT * FROM repositoryentity WHERE query = :query")
    fun getGitRepoSearchPagingSource(query: String): PagingSource<Int, RepositoryEntity>

    @Query("DELETE FROM repositoryentity WHERE query = :query")
    suspend fun clearQueryData(query: String)

    @Upsert
    suspend fun upsertRepository(repositories: List<RepositoryEntity>)

    @Query("SELECT EXISTS(SELECT 1 FROM repositoryentity WHERE query = :query)")
    suspend fun isQueryInDb(query: String): Boolean

}
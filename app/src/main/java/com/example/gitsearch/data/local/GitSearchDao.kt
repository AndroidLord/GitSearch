package com.example.gitsearch.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.gitsearch.data.local.entity.RepositoryEntity

@Dao
interface GitSearchDao {

    @Upsert
    suspend fun upsertRepository(repositories: List<RepositoryEntity>)

    @Query("SELECT * FROM repositoryentity")
    fun getGitRepoSearchPagingSource(): PagingSource<Int, RepositoryEntity>

    @Query("DELETE FROM repositoryentity")
    suspend fun clearAll()

}
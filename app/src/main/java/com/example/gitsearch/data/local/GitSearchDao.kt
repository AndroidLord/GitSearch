package com.example.gitsearch.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.gitsearch.data.local.entity.RepositoryEntity

@Dao
interface GitSearchDao {

    @Upsert
    suspend fun upsertRepository(repository: RepositoryEntity)

    @Delete
    suspend fun deleteRepository(repository: RepositoryEntity)

    @Query("SELECT * FROM repositoryentity")
    suspend fun getRepositories(): List<RepositoryEntity>

}